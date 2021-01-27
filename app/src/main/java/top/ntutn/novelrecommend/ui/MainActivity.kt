package top.ntutn.novelrecommend.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import top.ntutn.novelrecommend.BuildConfig
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @IdRes
    private var lastSelectedId: Int? = null
    private var clickCount = 0

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
        binding.navView.setOnLongClickListener { true } //阻止出现tooltip
        binding.navView.setOnNavigationItemSelectedListener {
            if (BuildConfig.DEBUG) {
                @IdRes
                val currentId = binding.navView.selectedItemId

                if (currentId == lastSelectedId) {
                    clickCount++
                    if (clickCount >= 5) {
                        clickCount = 0
                        lastSelectedId = null
                        DebugHelperActivity.actionStart(this)
                    }
                } else {
                    lastSelectedId = currentId
                    clickCount = 0
                }
            }
            true
        }
    }
}