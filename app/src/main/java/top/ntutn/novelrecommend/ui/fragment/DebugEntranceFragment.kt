package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import top.ntutn.novelrecommend.adapter.DebugEntranceAdapter
import top.ntutn.commonui.common.viewLifecycle
import top.ntutn.novelrecommend.databinding.FragmentDebugEntranceBinding
import top.ntutn.commonui.base.BaseFragment
import top.ntutn.novelrecommend.ui.viewmodel.DebugEntranceViewModel

class DebugEntranceFragment : BaseFragment() {
    private var binding by viewLifecycle<FragmentDebugEntranceBinding>()
    private val debugEntranceViewModel by viewModels<DebugEntranceViewModel>()
    private lateinit var adapter: DebugEntranceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebugEntranceBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
//        binding.entranceRecyclerView.adapter
        binding.entranceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DebugEntranceAdapter()
        binding.entranceRecyclerView.adapter = adapter
        debugEntranceViewModel.debugEntranceList.observe(viewLifecycleOwner) {
            adapter.dataList = it
        }
    }

    private fun initData() {
        debugEntranceViewModel.initData()
    }
}