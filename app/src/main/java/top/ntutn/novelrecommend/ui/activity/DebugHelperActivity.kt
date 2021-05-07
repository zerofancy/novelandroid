package top.ntutn.novelrecommend.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import top.ntutn.novelrecommend.adapter.DebugToolAdapter
import top.ntutn.novelrecommend.databinding.ActivityDebugHelperBinding
import top.ntutn.commonui.base.BaseActivity

class DebugHelperActivity : BaseActivity() {
    private lateinit var binding: ActivityDebugHelperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugHelperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }


    private fun initView() {
        title = "ZeroFancy的秘密Debug工具"

        binding.debugViewPager.adapter = DebugToolAdapter(this)

        TabLayoutMediator(binding.debugTab, binding.debugViewPager) { tab, position ->
            tab.text = listOf("入口","配置")[position]
        }.attach()
    }


    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, DebugHelperActivity::class.java)
            context.startActivity(intent)
        }
    }
}