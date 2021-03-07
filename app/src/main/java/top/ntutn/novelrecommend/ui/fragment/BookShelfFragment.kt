package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.adapter.BookShelfAdapter
import top.ntutn.novelrecommend.databinding.FragmentBookshelfBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment
import top.ntutn.novelrecommend.ui.viewmodel.main.BookShelfViewModel

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
        adapter = BookShelfAdapter().apply {
            onItemRemoveListener = {
                val book = bookShelfViewModel.books.value[it]
                bookShelfViewModel.removeBook(book)
            }
        }
        layoutManager = LinearLayoutManager(requireContext())
        binding.bookshelfRecyclerView.apply {
//            setOnItemMoveListener(object : OnItemMoveListener {
//                override fun onItemMove(
//                    srcHolder: RecyclerView.ViewHolder?,
//                    targetHolder: RecyclerView.ViewHolder?
//                ): Boolean {
//                    return false
//                }
//
//                override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder?) {
//                    val rawPosition = srcHolder?.layoutPosition ?: return
//                    // 只有书籍条目可以划走
//                    if (this@BookShelfFragment.adapter.getItemViewType(rawPosition) != BookShelfAdapter.ItemType.BOOK.ordinal) return
//                    val position = rawPosition - 1
//                    val book = bookShelfViewModel.books.value[position]
//                    bookShelfViewModel.removeBook(book)
//                }
//            })

            adapter = this@BookShelfFragment.adapter
            layoutManager = this@BookShelfFragment.layoutManager
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                addItemDecoration(
                    DefaultItemDecoration(
                        this@BookShelfFragment.requireContext().getColor(R.color.divider_color)
                    )
                )
            }
        }
        bookShelfViewModel.books.observe(viewLifecycleOwner) {
            adapter.bookList = it
        }
    }

    private fun initData() {
        bookShelfViewModel.initBookShelf()
    }
}