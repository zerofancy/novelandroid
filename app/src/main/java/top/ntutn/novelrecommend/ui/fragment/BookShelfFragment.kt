package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.ui.base.BaseFragment

class BookShelfFragment : BaseFragment() {

    private lateinit var bookShelfViewModel: BookShelfViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookShelfViewModel =
            ViewModelProvider(this).get(BookShelfViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bookshelf, container, false)
        val textView: TextView = root.findViewById(R.id.text_bookshelf)
        bookShelfViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}