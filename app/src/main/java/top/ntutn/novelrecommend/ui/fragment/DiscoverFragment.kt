package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import top.ntutn.novelrecommend.databinding.FragmentDiscoverBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment
import top.ntutn.readview.ReadTestActivity

class DiscoverFragment : BaseFragment() {
    private lateinit var binding: FragmentDiscoverBinding
    private val discoverViewModel by viewModels<DiscoverViewModel>()

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
        discoverViewModel.novelList.observe(viewLifecycleOwner, Observer {
            binding.textDiscover.text = it.joinToString()
        })
        binding.textDiscover.setOnClickListener {
            ReadTestActivity.actionStart(requireContext())
        }
    }

    private fun initData() {
        discoverViewModel.loadMore()
    }
}