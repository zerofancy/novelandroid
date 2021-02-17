package top.ntutn.novelrecommend.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import top.ntutn.commonutil.AppUtil
import top.ntutn.commonutil.MetricsUtil
import top.ntutn.commonutil.showToast
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.databinding.ActivityMainBinding
import top.ntutn.novelrecommend.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var exitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navHostFragment.post { initView() }
    }

    override fun onResume() {
        super.onResume()
        MetricsUtil.onEvent("main_resume")
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

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime <= EXIT_TIME) {
            AppUtil.finishAllActivities()
//            finish()
        } else {
            exitTime = System.currentTimeMillis()
            R.string.press_again_to_exit.showToast()
        }
    }

    companion object {
        // 两次返回退出的时间间隔
        private const val EXIT_TIME = 2000L
    }
}