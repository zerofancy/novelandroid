package top.ntutn.novelrecommend.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import top.ntutn.novelrecommend.adapter.DebugConfigAdapter
import top.ntutn.novelrecommend.databinding.ActivityDebugHelperBinding
import top.ntutn.novelrecommend.ui.base.BaseActivity

class DebugHelperActivity : BaseActivity() {
    private lateinit var binding: ActivityDebugHelperBinding
    private val debugConfigAdapter = DebugConfigAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugHelperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()
    }

    private fun initView() {
        title = "ZeroFancy的秘密Debug工具"

        binding.configList.layoutManager = LinearLayoutManager(this)
        binding.configList.adapter = debugConfigAdapter
    }

    private fun initData() {
        debugConfigAdapter.configList = listOf("1", "2")
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, DebugHelperActivity::class.java)
            context.startActivity(intent)
        }
    }
}