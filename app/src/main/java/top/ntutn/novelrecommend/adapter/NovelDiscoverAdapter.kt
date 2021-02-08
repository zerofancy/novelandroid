package top.ntutn.novelrecommend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import top.ntutn.novelrecommend.databinding.ItemNovelDiscoverBinding
import top.ntutn.novelrecommend.model.NovelModel
import top.ntutn.novelrecommend.utils.showToast
import top.ntutn.readview.ReadView

class NovelDiscoverAdapter : RecyclerView.Adapter<NovelDiscoverAdapter.ViewHolder>() {
    var novelList: List<NovelModel> = listOf()
        set(value) {
            DiffUtil.calculateDiff(SimpleListDiffCallback(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    class ViewHolder(val binding: ItemNovelDiscoverBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNovelDiscoverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            readView.setText(novelList[position].content ?: "")
            readView.setOnItemSelectListener(object : ReadView.OnItemSelectListener {
                override fun onPagePreviousClicked() {
                    readView.goPreviousPage()
                }

                override fun onPageNextClicked() {
                    readView.goNextPage()
                }

                override fun onMenuClicked() {
                    "显示菜单".showToast()
                }
            })
        }
    }

    override fun getItemCount() = novelList.size
}

