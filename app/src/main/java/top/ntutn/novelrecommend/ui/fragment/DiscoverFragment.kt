package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager
import top.ntutn.novelrecommend.adapter.NovelDiscoverAdapter
import top.ntutn.commonui.common.viewLifecycle
import top.ntutn.novelrecommend.databinding.FragmentDiscoverBinding
import top.ntutn.commonui.base.BaseFragment
import top.ntutn.novelrecommend.ui.viewmodel.main.DiscoverViewModel

class DiscoverFragment : BaseFragment(), OnViewPagerListener {
    private var binding by viewLifecycle<FragmentDiscoverBinding>()
    private val discoverViewModel by activityViewModels<DiscoverViewModel>()
    private lateinit var adapter: NovelDiscoverAdapter
    private lateinit var layoutManager: ViewPagerLayoutManager

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
        adapter = NovelDiscoverAdapter(this)
        layoutManager = ViewPagerLayoutManager(requireContext(), ViewPagerLayoutManager.VERTICAL)
        layoutManager.setOnViewPagerListener(this)
        binding.discoverRecycler.apply {
            layoutManager = this@DiscoverFragment.layoutManager
            adapter = this@DiscoverFragment.adapter
        }
        discoverViewModel.novelList.observe(viewLifecycleOwner) {
            adapter.novelList = it
        }
        layoutManager.scrollToPosition(discoverViewModel.currentPosition.value)
    }

    private fun initData() {
        discoverViewModel.tryLoadMore()
    }

    override fun onInitComplete() = Unit

    override fun onPageRelease(isNext: Boolean, position: Int) = Unit

    override fun onPageSelected(position: Int, isBottom: Boolean) {
        if (discoverViewModel.currentPosition.value == position) return
        discoverViewModel.tryLoadMore()
        discoverViewModel.scrollTo(position)
    }
}