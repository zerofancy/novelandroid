package top.ntutn.novelrecommend.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import top.ntutn.novelrecommend.databinding.ItemDebugEntranceBinding

class DebugEntranceAdapter :
    RecyclerView.Adapter<CommonViewHolder<ViewBinding>>() {
    private val diffCallback = CommonDiffCallback()

    var dataList: List<DebugEntrance> = listOf()
        set(value) {
            diffCallback.oldList = field
            diffCallback.newList = value
            DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
            field = value
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
                holder.viewBinding.title.text = dataList[position].title
                holder.viewBinding.owner.text = dataList[position].owner
                holder.viewBinding.root.setOnClickListener { dataList[position].operation() }
            }
            else -> Unit
        }
    }

    override fun getItemCount(): Int = dataList.size
}

class DebugEntrance(val title: String, val owner: String, val operation: () -> Unit) :
    CommonMutiItem {
    override fun getType(): Int = TYPE_ENTRANCE

    companion object {
        const val TYPE_ENTRANCE = 0
    }
}

