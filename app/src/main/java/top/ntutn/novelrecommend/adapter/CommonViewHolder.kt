package top.ntutn.novelrecommend.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class CommonViewHolder<T:ViewBinding>(val viewBinding: T):RecyclerView.ViewHolder(viewBinding.root)