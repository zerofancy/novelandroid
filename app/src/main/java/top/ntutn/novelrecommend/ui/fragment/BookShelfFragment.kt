package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import top.ntutn.novelrecommend.adapter.BookShelfAdapter
import top.ntutn.novelrecommend.databinding.FragmentBookshelfBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment
import top.ntutn.novelrecommend.ui.viewmodel.BookShelfViewModel

class BookShelfFragment : BaseFragment() {
    private val bookShelfViewModel by activityViewModels<BookShelfViewModel>()
    private lateinit var binding: FragmentBookshelfBinding
    private lateinit var adapter: BookShelfAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookshelfBinding.inflate(inflater, container, false)
        initView()
        initData()
        return binding.root
    }

    private fun initView() {
        adapter = BookShelfAdapter()
        layoutManager = LinearLayoutManager(requireContext())
        binding.bookshelfRecyclerView.adapter = adapter
        binding.bookshelfRecyclerView.layoutManager = layoutManager
        binding.bookshelfRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        bookShelfViewModel.books.observe(viewLifecycleOwner) {
            adapter.bookList = it
        }
    }

    private fun initData() {
        bookShelfViewModel.initBookShelf()
    }
}