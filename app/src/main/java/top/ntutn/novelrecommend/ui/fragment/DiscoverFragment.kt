package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import top.ntutn.novelrecommend.adapter.NovelDiscoverAdapter
import top.ntutn.novelrecommend.databinding.FragmentDiscoverBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment

class DiscoverFragment : BaseFragment() {
    private lateinit var binding: FragmentDiscoverBinding
    private val discoverViewModel by activityViewModels<DiscoverViewModel>()
    private val adapter = NovelDiscoverAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        initView()
        initData()
        return binding.root
    }

    private fun initView() {
        binding.discoverRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DiscoverFragment.adapter
            PagerSnapHelper().attachToRecyclerView(this)
        }
        discoverViewModel.novelList.observe(viewLifecycleOwner) {
            adapter.novelList = it
        }
//        binding.textDiscover.setOnClickListener {
//            ReadTestActivity.actionStart(requireContext())
//        }
    }

    private fun initData() {
        discoverViewModel.loadMore()
    }
}