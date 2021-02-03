package top.ntutn.novelrecommend.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.Subscribe
import top.ntutn.novelrecommend.adapter.DebugConfigAdapter
import top.ntutn.novelrecommend.databinding.ActivityDebugHelperBinding
import top.ntutn.novelrecommend.ui.base.BaseActivity
import top.ntutn.novelrecommend.ui.event.ConfigEditDialogCloseEvent

class DebugHelperActivity : BaseActivity() {
    private lateinit var binding: ActivityDebugHelperBinding
    private val debugConfigViewModel by viewModels<DebugConfigViewModel>()
    private val debugConfigAdapter = DebugConfigAdapter(this)

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
        debugConfigViewModel.configList.observe(this) {
            debugConfigAdapter.configList = it
        }
    }

    private fun initData() {
        debugConfigViewModel.initList()
    }

    @Subscribe
    fun onConfigEditDialogCloseEvent(event: ConfigEditDialogCloseEvent) {
        if (!event.saveResult || event.key == null || event.value == null) return
        debugConfigViewModel.updateConfig(event.key, event.value)
    }

    companion object {
        const val REQUEST_EDIT_DIALOG = 0

        fun actionStart(context: Context) {
            val intent = Intent(context, DebugHelperActivity::class.java)
            context.startActivity(intent)
        }
    }
}