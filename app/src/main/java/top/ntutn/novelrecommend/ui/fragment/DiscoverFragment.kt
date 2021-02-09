package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import top.ntutn.novelrecommend.adapter.NovelDiscoverAdapter
import top.ntutn.novelrecommend.databinding.FragmentDiscoverBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment

class DiscoverFragment : BaseFragment() {
    private lateinit var binding: FragmentDiscoverBinding
    private val discoverViewModel by activityViewModels<DiscoverViewModel>()
    private lateinit var adapter: NovelDiscoverAdapter
    private lateinit var layoutManager: LinearLayoutManager

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
        adapter = NovelDiscoverAdapter(parentFragmentManager)
        layoutManager = LinearLayoutManager(requireContext())
        binding.discoverRecycler.apply {
            layoutManager = this@DiscoverFragment.layoutManager
            adapter = this@DiscoverFragment.adapter
            PagerSnapHelper().attachToRecyclerView(this)
        }
        binding.discoverRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisiblePosition: Int = layoutManager.findLastVisibleItemPosition()
                discoverViewModel.tryLoadMore()
                discoverViewModel.scrollTo(lastVisiblePosition)
            }
        })
        discoverViewModel.novelList.observe(viewLifecycleOwner) {
            adapter.novelList = it
        }
        discoverViewModel.currentPosition.value?.let {
            layoutManager.scrollToPosition(it)
        }
    }

    private fun initData() {
        discoverViewModel.tryLoadMore()
    }
}