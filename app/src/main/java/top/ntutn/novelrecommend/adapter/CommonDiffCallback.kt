package top.ntutn.novelrecommend.adapter

import androidx.recyclerview.widget.DiffUtil

open class CommonDiffCallback<T : CommonMutiItem>(

) : DiffUtil.Callback() {
    lateinit var oldList: List<T>
    lateinit var newList: List<T>

    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].getType() == newList[newItemPosition].getType()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}