package top.ntutn.novelrecommend.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import top.ntutn.commonui.base.BaseActivity
import top.ntutn.commonutil.AppUtil
import top.ntutn.commonutil.showSnackBar
import top.ntutn.novelrecommend.databinding.ActivityNovelReadBinding
import top.ntutn.novelrecommend.ui.viewmodel.NovelReadViewModel

/**
 * 阅读小说的Activity
 */
class NovelReadActivity : BaseActivity() {
    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private const val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private const val AUTO_HIDE_DELAY_MILLIS = 300

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private const val UI_ANIMATION_DELAY = 300

        private const val PARAM_NOVEL_ID = "novel_id"
        private const val PARM_FULL_SCREEN = "full_screen"

        /**
         * @param id 小说id
         */
        fun actionStart(context: Context, id: Long, fullscreen: Boolean = false) {
            val intent = Intent(context, NovelReadActivity::class.java).apply {
                putExtra(PARAM_NOVEL_ID, id)
                putExtra(PARM_FULL_SCREEN, fullscreen)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityNovelReadBinding
    private lateinit var fullscreenContent: TextView
    private lateinit var fullscreenContentControls: ViewGroup
    private val hideHandler = Handler(Looper.getMainLooper())
    private val novelReadViewModel by viewModels<NovelReadViewModel>()

    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        fullscreenContent.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreenContentControls.visibility = View.VISIBLE
    }
    private var isFullscreen: Boolean = false

    private val hideRunnable = Runnable { hide() }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val delayHideTouchListener = View.OnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS)
            }
            MotionEvent.ACTION_UP -> view.performClick()
            else -> {
            }
        }
        false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNovelReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up the user interaction to manually show or hide the system UI.
        fullscreenContent = binding.fullscreenContent
//        fullscreenContent.setOnClickListener { toggle() }
        binding.statusBarPlaceHolder.layoutParams =
            binding.statusBarPlaceHolder.layoutParams.apply {
                height = AppUtil.getStatusBarHeightCompat(this@NovelReadActivity)
            }
        show()

        fullscreenContentControls = binding.fullscreenContentControls

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        binding.startReadButton.setOnTouchListener(delayHideTouchListener)

        novelReadViewModel.fetchNovelInfo(intent.getLongExtra(PARAM_NOVEL_ID, 0))
        novelReadViewModel.title.observe(this) {
            title = it
        }
        novelReadViewModel.bookInfo.observe(this) {
            binding.coverImageView.setImageURI(it?.cover)
            binding.bookTitleTextView.text = it?.title
            binding.authorTextView.text = "@${it?.author}"
            binding.tagsTextView.text = it?.tags?.joinToString()
            binding.descriptionTextView.text = it?.description
        }

        // 点赞收藏分享
        binding.apply {
            likeButton.setOnClickListener {
                likeButton.toggle()
            }
            starButton.setOnClickListener {
                starButton.toggle()
            }
            shareButton.setOnClickListener { shareButton.showSnackBar("分享功能暂时不可用") }
        }
        // 初始化点赞状态
//        binding.starButton.setCheckedWithoutAnimator((bookShelfViewModel.books.value.find { it.id == currentNovel.id }) != null)
//        binding.likeButton.setCheckedWithoutAnimator(discoverViewModel.novelList.value[discoverViewModel.currentPosition.value].isLiked)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        if (intent.getBooleanExtra(PARM_FULL_SCREEN, false)) {
            delayedHide(100)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return false
        }
        return true
    }

    override fun onBackPressed() {
        if (isFullscreen) {
            show()
            return
        }
        super.onBackPressed()
    }


    private fun toggle() {
        if (isFullscreen) {
            show()
        } else {
            hide()
        }
    }

    /**
     * 进入全屏状态（隐藏systemui）
     */
    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        fullscreenContentControls.visibility = View.GONE
        isFullscreen = true

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.removeCallbacks(showPart2Runnable)
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * 退出全屏状态
     */
    private fun show() {
        // Show the system bar
        fullscreenContent.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        isFullscreen = false

        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, delayMillis.toLong())
    }
}