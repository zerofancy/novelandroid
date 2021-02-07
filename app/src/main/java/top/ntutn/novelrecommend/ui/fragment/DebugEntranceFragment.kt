package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import top.ntutn.novelrecommend.adapter.CommonDiffCallback
import top.ntutn.novelrecommend.adapter.CommonMutiItem
import top.ntutn.novelrecommend.adapter.DebugEntranceAdapter
import top.ntutn.novelrecommend.databinding.FragmentDebugEntranceBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment

class DebugEntranceFragment : BaseFragment() {
    private lateinit var binding: FragmentDebugEntranceBinding
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
        adapter = DebugEntranceAdapter(requireContext())
        binding.entranceRecyclerView.adapter = adapter
        debugEntranceViewModel.debugEntranceList.observe(viewLifecycleOwner) {
            adapter.dataList = it
        }
    }

    private fun initData() {
        debugEntranceViewModel.initData()
    }
}