package top.ntutn.novelrecommend.ui.activity

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.databinding.ActivityMainBinding
import top.ntutn.novelrecommend.ui.base.BaseActivity
import top.ntutn.novelrecommend.utils.AppUtil
import top.ntutn.novelrecommend.utils.showToast

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    @IdRes
    private var lastSelectedId: Int? = null
    private var clickCount = 0
    private var exitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_discover, R.id.navigation_bookshelf, R.id.navigation_me
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime <= EXIT_TIME) {
            AppUtil.finishAllActivities()
            super.onBackPressed()
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