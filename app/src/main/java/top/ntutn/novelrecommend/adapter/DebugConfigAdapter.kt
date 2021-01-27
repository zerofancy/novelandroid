package top.ntutn.novelrecommend.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import top.ntutn.novelrecommend.databinding.ItemDebugConfigBinding

class DebugConfigAdapter : RecyclerView.Adapter<DebugConfigAdapter.ViewHolder>() {
    var configList: List<String> = listOf()
        set(value) {
            DiffUtil.calculateDiff(DebugConfigDiffCallback(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    class ViewHolder(val binding: ItemDebugConfigBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDebugConfigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.configKeyTextView.text = configList[position]
        holder.binding.root.setBackgroundColor(if (position % 2 == 1) Color.LTGRAY else Color.WHITE)
    }

    override fun getItemCount(): Int = configList.size
}

class DebugConfigDiffCallback(
    private val oldList: List<String>,
    private val newList: List<String>
) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}