package top.ntutn.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import top.ntutn.commonui.common.CommonViewHolder
import top.ntutn.setting.databinding.ItemMeFootBinding
import top.ntutn.setting.databinding.ItemMeSettingBinding

class MeSettingAdapter : RecyclerView.Adapter<CommonViewHolder<ViewBinding>>() {
    enum class ItemType {
        SETTING,
        FOOTER
    }

    data class MeSetting(
        val key: String,
        val iconRes: Int,
        val title: String,
        val callback: (View) -> Unit
    )

    private val settingItems = mutableListOf<MeSetting>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<ViewBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            ItemType.SETTING.ordinal -> ItemMeSettingBinding.inflate(inflater, parent, false)
            else -> ItemMeFootBinding.inflate(inflater, parent, false)
        }
        return CommonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonViewHolder<ViewBinding>, position: Int) {
        if (getItemViewType(position) == ItemType.SETTING.ordinal) {
            val binding = holder.viewBinding as ItemMeSettingBinding
            binding.iconImageView.setImageResource(settingItems[position].iconRes)
            binding.titleTextView.text = settingItems[position].title
            binding.root.setOnClickListener { settingItems[position].callback.invoke(it) }
        } else {
            val binding = holder.viewBinding as ItemMeFootBinding
            binding.textView.text =
                "Version(${if (BuildConfig.DEBUG) "DEBUG" else "RELEASE"}): v${SettingService.versionNameHolder}"
        }
    }

    override fun getItemCount(): Int = settingItems.size + 1

    override fun getItemViewType(position: Int): Int =
        if (position in settingItems.indices) ItemType.SETTING.ordinal else ItemType.FOOTER.ordinal

    fun addSettingItem(item: MeSetting) {
        settingItems.add(item)
        notifyDataSetChanged()
    }
}