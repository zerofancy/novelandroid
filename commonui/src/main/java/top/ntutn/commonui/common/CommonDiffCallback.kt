package top.ntutn.commonui.common

import androidx.recyclerview.widget.DiffUtil

open class CommonDiffCallback : DiffUtil.Callback() {
    lateinit var oldList: List<CommonMutiItem>
    lateinit var newList: List<CommonMutiItem>

    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].getType() == newList[newItemPosition].getType() //TODO 有问题

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}

class SimpleListDiffCallback<T>(private val oldList: List<T>, private val newList: List<T>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}