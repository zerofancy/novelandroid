package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import top.ntutn.commonui.base.BaseFragment
import top.ntutn.commonui.common.SimpleItemDecoration
import top.ntutn.commonui.common.viewLifecycle
import top.ntutn.novelrecommend.adapter.BookShelfAdapter
import top.ntutn.novelrecommend.databinding.FragmentBookshelfBinding
import top.ntutn.novelrecommend.ui.viewmodel.main.BookShelfViewModel

class BookShelfFragment : BaseFragment() {
    private val bookShelfViewModel by activityViewModels<BookShelfViewModel>()
    private var binding by viewLifecycle<FragmentBookshelfBinding>()
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
        adapter = BookShelfAdapter().apply {
            onItemRemoveListener = {
                val book = bookShelfViewModel.books.value[it]
                bookShelfViewModel.removeBook(book)
            }
        }
        layoutManager = LinearLayoutManager(requireContext())
        binding.bookshelfRecyclerView.apply {
            adapter = this@BookShelfFragment.adapter
            layoutManager = this@BookShelfFragment.layoutManager
            addItemDecoration(
                SimpleItemDecoration(
                    this@BookShelfFragment.requireContext(),
                    SimpleItemDecoration.VERTICAL
                )
            )
        }
        bookShelfViewModel.books.observe(viewLifecycleOwner) {
            adapter.bookList = it
        }
    }

    private fun initData() {
        bookShelfViewModel.initBookShelf()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }
}