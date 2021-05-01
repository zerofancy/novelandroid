package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.Subscribe
import top.ntutn.novelrecommend.adapter.DebugConfigAdapter
import top.ntutn.novelrecommend.common.viewLifecycle
import top.ntutn.novelrecommend.databinding.FragmentDebugConfigBinding
import top.ntutn.novelrecommend.ui.viewmodel.DebugConfigViewModel
import top.ntutn.novelrecommend.ui.base.BaseFragment
import top.ntutn.novelrecommend.ui.dialog.ConfigEditDialogCloseEvent
import top.ntutn.novelrecommend.utils.EventBusWrapper

class DebugConfigFragment : BaseFragment() {
    private var binding by viewLifecycle<FragmentDebugConfigBinding>()
    private val debugConfigViewModel by viewModels<DebugConfigViewModel>()
    private lateinit var debugConfigAdapter: DebugConfigAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugConfigViewModel.initList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebugConfigBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        debugConfigAdapter = DebugConfigAdapter(this)
        binding.configList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.configList.adapter = debugConfigAdapter

        debugConfigViewModel.configList.observe(viewLifecycleOwner) {
            debugConfigAdapter.configList = it
        }
    }

    override fun onStart() {
        super.onStart()
        EventBusWrapper.register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBusWrapper.unregister(this)
    }

    @Subscribe
    fun onConfigEditDialogCloseEvent(event: ConfigEditDialogCloseEvent) {
        if (!event.saveResult || event.key == null || event.value == null) return
        debugConfigViewModel.updateConfig(event.key, event.value)
    }

    companion object {
        const val REQUEST_CONFIG_EDIT = 0
    }
}