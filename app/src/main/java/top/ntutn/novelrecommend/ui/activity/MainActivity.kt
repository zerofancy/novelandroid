package top.ntutn.novelrecommend.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.smile.analytics_lib_api.MetricsService
import top.ntutn.commonutil.AppUtil
import top.ntutn.commonutil.MetricsUtil
import top.ntutn.commonutil.showToast
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.databinding.ActivityMainBinding
import top.ntutn.novelrecommend.ui.base.BaseActivity
import top.ntutn.novelrecommend.ui.viewmodel.main.BookShelfViewModel
import top.ntutn.novelrecommend.ui.viewmodel.main.DiscoverViewModel
import top.ntutn.novelrecommend.utils.TimeUtil

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val bookShelfViewModel by viewModels<BookShelfViewModel>()
    private val discoverViewModel by viewModels<DiscoverViewModel>()

    private var exitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calculateStartTime()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navHostFragment.post {
            initView()
            initData()
        }
    }

    private fun initView() {
        navController = binding.navHostFragment.findNavController()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_discover, R.id.navigation_bookshelf, R.id.navigation_me
            )
        )
        // 与ActionBar联动
        //setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        getString(R.string.debug_notice).showToast()
    }

    private fun initData() {
        bookShelfViewModel.initBookShelf()
        discoverViewModel.loadMore()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime <= EXIT_TIME) {
            AppUtil.finishAllActivities()
            startActivity(Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_HOME) })
        } else {
            exitTime = System.currentTimeMillis()
            R.string.press_again_to_exit.showToast()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && true
        /**没有经过广告或者引导页**/
        ) {
            val hotStartTime = TimeUtil.getTimeCalculate(TimeUtil.HOT_START)
            if (TimeUtil.sColdStartTime > 0 && hotStartTime > 0) {
                // 真正的冷启动时间 = Application启动时间 + 热启动时间
                val coldStartTime = TimeUtil.sColdStartTime + hotStartTime;
                // 过滤掉异常启动时间
                if (coldStartTime < 50000) {
                    // 上传冷启动时间coldStartTime
                    MetricsService.getInstance()?.onEvent(
                        "cold_start", mapOf(
                            "coldStartTime" to coldStartTime.toString()
                        )
                    )
                }
            } else if (hotStartTime > 0) {
                // 过滤掉异常启动时间
                if (hotStartTime < 30000) {
                    // 上传热启动时间hotStartTime
                    MetricsService.getInstance()?.onEvent(
                        "hot_start", mapOf(
                            "hotStartTime" to hotStartTime.toString()
                        )
                    )
                }
            }
        }
    }

    private fun calculateStartTime() {
        val coldStartTime: Long = TimeUtil.getTimeCalculate(TimeUtil.COLD_START)
        // 这里记录的TimeUtils.coldStartTime是指Application启动的时间，最终的冷启动时间等于Application启动时间+热启动时间
        TimeUtil.sColdStartTime = if (coldStartTime > 0) coldStartTime else 0
        TimeUtil.beginTimeCalculate(TimeUtil.HOT_START)
    }

    companion object {
        // 两次返回退出的时间间隔
        private const val EXIT_TIME = 2000L

        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}