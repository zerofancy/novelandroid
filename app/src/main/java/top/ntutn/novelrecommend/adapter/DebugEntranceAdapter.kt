package top.ntutn.novelrecommend.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import top.ntutn.commonui.common.CommonDiffCallback
import top.ntutn.commonui.common.CommonMutiItem
import top.ntutn.commonui.common.CommonViewHolder
import top.ntutn.novelrecommend.databinding.ItemDebugEntranceBinding

class DebugEntranceAdapter :
    RecyclerView.Adapter<CommonViewHolder<ViewBinding>>() {
    private val diffCallback = CommonDiffCallback()

    var dataList: List<DebugEntrance> = listOf()
        set(value) {
            diffCallback.oldList = field
            diffCallback.newList = value
            DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
            field = value.toList()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<ViewBinding> = when (viewType) {
        DebugEntrance.TYPE_ENTRANCE -> {
            val binding =
                ItemDebugEntranceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CommonViewHolder(binding)
        }
        else -> {
            throw IllegalArgumentException("未知参数类型")
        }
    }

    override fun onBindViewHolder(holder: CommonViewHolder<ViewBinding>, position: Int) {
        holder.viewBinding.root.setBackgroundColor(if (position % 2 == 1) Color.LTGRAY else Color.WHITE)
        when (holder.viewBinding) {
            is ItemDebugEntranceBinding -> {
                (holder.viewBinding as ItemDebugEntranceBinding).title.text = dataList[position].title
                (holder.viewBinding as ItemDebugEntranceBinding).owner.text = dataList[position].owner
                holder.viewBinding.root.setOnClickListener { dataList[position].operation(holder.viewBinding.root.context) }
            }
            else -> Unit
        }
    }

    override fun getItemCount(): Int = dataList.size
}

class DebugEntrance(val title: String, val owner: String, val operation: (context:Context) -> Unit) :
    CommonMutiItem {
    override fun getType(): Int = TYPE_ENTRANCE

    companion object {
        const val TYPE_ENTRANCE = 0
    }
}

