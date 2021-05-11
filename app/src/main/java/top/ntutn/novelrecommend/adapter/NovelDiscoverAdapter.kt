package top.ntutn.novelrecommend.adapter

import android.graphics.RectF
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import top.ntutn.commonutil.showLongToast
import top.ntutn.novelrecommend.common.SimpleListDiffCallback
import top.ntutn.novelrecommend.databinding.ItemNovelDiscoverBinding
import top.ntutn.novelrecommend.model.NovelModel
import top.ntutn.novelrecommend.ui.fragment.DiscoverFragment
import top.ntutn.novelrecommend.ui.viewmodel.main.DiscoverViewModel

class NovelDiscoverAdapter(private val discoverFragment: DiscoverFragment) :
    RecyclerView.Adapter<NovelDiscoverAdapter.ViewHolder>() {
    private val discoverViewModel by discoverFragment.activityViewModels<DiscoverViewModel>()

    var novelList: List<NovelModel> = listOf()
        set(value) {
            DiffUtil.calculateDiff(SimpleListDiffCallback(field, value)).dispatchUpdatesTo(this)
            field = value.toList()
        }

    class ViewHolder(val binding: ItemNovelDiscoverBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNovelDiscoverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            readView.post {
                readView.apply {
                    addClickEventListener(RectF(0f, 0f, (width / 3).toFloat(), height.toFloat())) {
                        if (isFirstPage()) {
                            "已是第一页".showLongToast()
                        } else {
                            goPrevPage()
                        }
                    }
                    addClickEventListener(
                        RectF(
                            width / 3f,
                            0f,
                            width / 3f * 2f,
                            height.toFloat()
                        )
                    ) {
                        "显示菜单……".showLongToast()
                    }
                    addClickEventListener(
                        RectF(
                            (width / 3 * 2).toFloat(),
                            0f,
                            width.toFloat(),
                            height.toFloat()
                        )
                    ) {
                        if(isLastPage()){
                            "已是最后一页".showLongToast()
                        }else{
                            goNextPage()
                        }
                    }

                }
            }
            readView.text = novelList[position].content ?: ""
//            readView.setOnItemSelectListener(object : ReadView.OnItemSelectListener {
//                override fun onSwitchPage(index: Int) {
//                    discoverViewModel.switchPage(readView.getPageCount(), index)
//                }
//
//                override fun onPagePreviousClicked(isFirstPage: Boolean) {
//                    if (isFirstPage) {
////                        "已是第一页".showLongToast()
//                        return
//                    }
//                    readView.goPreviousPage()
//                }
//
//                override fun onPageNextClicked(isLastPage: Boolean) {
//                    if (isLastPage) {
////                        "已是最后一页".showLongToast()
//                        NovelDetailDialogFragment.newInstance()
//                            .show(discoverFragment.parentFragmentManager, "detail")
//                        return
//                    }
//                    readView.goNextPage()
//                }
//
//                override fun onMenuClicked() {
//                    "显示菜单".showToast()
//                }
//            })
        }
    }

    override fun getItemCount() = novelList.size
}

