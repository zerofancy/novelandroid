package top.ntutn.novelrecommend.adapter

import android.graphics.RectF
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import top.ntutn.commonui.common.SimpleListDiffCallback
import top.ntutn.commonutil.showLongToast
import top.ntutn.novelrecommend.databinding.ItemNovelDiscoverBinding
import top.ntutn.novelrecommend.model.NovelModel
import top.ntutn.novelrecommend.ui.activity.NovelReadActivity
import top.ntutn.novelrecommend.ui.dialog.NovelDetailDialogFragment
import top.ntutn.novelrecommend.ui.fragment.DiscoverFragment
import top.ntutn.novelrecommend.ui.viewmodel.main.DiscoverViewModel
import top.ntutn.novelrecommend.utils.ReadViewHelper
import top.ntutn.readview.BreakReadView
import top.ntutn.setting.SettingKey
import top.ntutn.setting.SettingList
import top.ntutn.setting.SettingServiceDelegate

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
            ReadViewHelper.updateFontSetting(readView)
            readView.post {
                readView.setCurrentPage(0)
                readView.apply {
                    clearClickEventListener()
                    addClickEventListener(RectF(0f, 0f, (width / 3).toFloat(), height.toFloat())) {
                        if (isFirstPage()) {
                            "已是第一页".showLongToast()
                        } else {
                            discoverViewModel.switchPage(getPageCount(), getCurrentPage())
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
                        NovelDetailDialogFragment.newInstance()
                            .show(discoverFragment.parentFragmentManager, "detail")
                    }
                    addClickEventListener(
                        RectF(
                            (width / 3 * 2).toFloat(),
                            0f,
                            width.toFloat(),
                            height.toFloat()
                        )
                    ) {
                        if (isLastPage()) {
                            NovelReadActivity.actionStart(
                                context,
                                novelList[position].id ?: 0,
                                true
                            )
                        } else {
                            goNextPage()
                        }
                    }
                }
            }
            readView.text = novelList[position].description ?: ""
        }
    }

    override fun getItemCount() = novelList.size
}