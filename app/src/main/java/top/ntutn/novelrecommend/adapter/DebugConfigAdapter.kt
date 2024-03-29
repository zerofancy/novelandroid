package top.ntutn.novelrecommend.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.databinding.ItemDebugConfigBinding
import top.ntutn.novelrecommend.databinding.ItemDebugConfigScopeBinding
import top.ntutn.novelrecommend.model.DebugConfigListModel
import top.ntutn.novelrecommend.ui.dialog.ConfigEditDialogFragment

class DebugConfigAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<DebugConfigAdapter.ViewHolder>() {
    var configList: List<DebugConfigListModel> = listOf()
        set(value) {
            DiffUtil.calculateDiff(DebugConfigDiffCallback(field, value)).dispatchUpdatesTo(this)
            field = value.toList()
        }

    class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = when (viewType) {
            DebugConfigListModel.TYPE_CONFIG_ITEM -> ItemDebugConfigBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
            DebugConfigListModel.TYPE_SCOPE -> ItemDebugConfigScopeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
            else -> throw IllegalArgumentException("没见过的参数类型:${viewType}")
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = configList[position]
        when (holder.binding) {
            is ItemDebugConfigBinding -> {
                holder.binding.apply {
                    root.setBackgroundColor(if (position % 2 == 1) Color.LTGRAY else Color.WHITE)
                    fragment.getString(R.string.template_owner)
                        .format(data.configInformation?.owner).also { ownerTextView.text = it }
                    scopeTextView.text = data.configInformation?.scope
                    titleTextView.text = data.configInformation?.title
                    keyTextView.text = data.configInformation?.key
                    root.setOnClickListener {
                        fragment.parentFragmentManager.let { it1 ->
                            ConfigEditDialogFragment.newInstance(
                                data.configInformation?.key ?: ""
                            ).show(it1, "Edit")
                        }
                    }
                }
            }
            is ItemDebugConfigScopeBinding -> {
                holder.binding.apply {
                    titleTextView.text = data.title

                }
            }
            else -> throw IllegalArgumentException("没见过的参数类型:${holder.binding}")
        }
    }

    override fun getItemCount(): Int = configList.size

    override fun getItemViewType(position: Int): Int = configList[position].type
}

class DebugConfigDiffCallback(
    private val oldList: List<DebugConfigListModel>,
    private val newList: List<DebugConfigListModel>
) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].type == newList[newItemPosition].type

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}