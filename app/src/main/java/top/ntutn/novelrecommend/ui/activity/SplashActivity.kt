package top.ntutn.novelrecommend.ui.activity

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import top.ntutn.commonutil.showToast
import top.ntutn.novelrecommend.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())
    private val waitRunnable = Runnable {
        isWaitFinished = true
        tryStartMainActivity()
    }
    private var isAnimFinished = false
    private var isWaitFinished = false

    private fun tryStartMainActivity() {
        if (!isAnimFinished || !isWaitFinished) return
        MainActivity.actionStart(this)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initView()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(waitRunnable, 5000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(waitRunnable)
    }

    override fun onBackPressed() = Unit

    private fun initView() {
        binding.animLogo.apply {
            addOffsetAnimListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) = Unit
                override fun onAnimationEnd(animation: Animator?) = Unit
                override fun onAnimationCancel(animation: Animator?) = Unit
                override fun onAnimationRepeat(animation: Animator?) = Unit
            })
            addGradientAnimListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) = Unit
                override fun onAnimationEnd(animation: Animator?) {
                    isAnimFinished = true
                    tryStartMainActivity()
                }

                override fun onAnimationCancel(animation: Animator?) = Unit
                override fun onAnimationRepeat(animation: Animator?) = Unit
            })
            startAnimation()
        }
    }
}