package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.ui.base.BaseFragment

class DiscoverFragment : BaseFragment() {

    private val discoverViewModel by viewModels<DiscoverViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_discover, container, false)
        val textView: TextView = root.findViewById(R.id.text_discover)
        discoverViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}