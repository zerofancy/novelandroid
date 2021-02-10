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
import top.ntutn.novelrecommend.ui.viewmodel.BookShelfViewModel

class BookShelfFragment : BaseFragment() {
    private val bookShelfViewModel by viewModels<BookShelfViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_bookshelf, container, false)
        val textView: TextView = root.findViewById(R.id.text_bookshelf)
        bookShelfViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}