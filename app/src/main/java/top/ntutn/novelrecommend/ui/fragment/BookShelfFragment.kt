package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration
import top.ntutn.commonutil.showToast
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
        adapter = BookShelfAdapter()
        layoutManager = LinearLayoutManager(requireContext())
        binding.bookshelfRecyclerView.apply {
            setOnItemClickListener { _, adapterPosition ->
                bookShelfViewModel.books.value[adapterPosition].title?.showToast()
                // TODO 点击详细介绍
            }
            setOnItemLongClickListener { _, adapterPosition ->
                "长按菜单${bookShelfViewModel.books.value[adapterPosition].title}".showToast()
                // TODO 长按删除
            }
            // 允许拖拽排序 （貌似与长按动作冲突）
            isLongPressDragEnabled = false
            // 允许侧滑删除
            isItemViewSwipeEnabled = true
            setOnItemMoveListener(object : OnItemMoveListener {
                override fun onItemMove(
                    srcHolder: RecyclerView.ViewHolder?,
                    targetHolder: RecyclerView.ViewHolder?
                ): Boolean {
                    return false
                }

                override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder?) {
                    val position = srcHolder?.layoutPosition ?: return
                    val book = bookShelfViewModel.books.value[position]
                    bookShelfViewModel.removeBook(book)
                }
            })

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