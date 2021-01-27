package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import top.ntutn.novelrecommend.BuildConfig
import top.ntutn.novelrecommend.databinding.FragmentMeBinding
import top.ntutn.novelrecommend.ui.activity.DebugHelperActivity
import top.ntutn.novelrecommend.ui.base.BaseFragment

class MeFragment : BaseFragment() {
    private lateinit var binding: FragmentMeBinding
    private val meViewModel by viewModels<MeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeBinding.inflate(inflater)

        initView()

        return binding.root
    }

    private fun initView() {
        val textView: TextView = binding.textMe
        meViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        if (BuildConfig.DEBUG) {
            binding.debugButton.visibility = View.VISIBLE
            binding.debugButton.setOnClickListener {
                DebugHelperActivity.actionStart(this.requireContext())
            }
        }
    }
}