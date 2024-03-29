package top.ntutn.setting

import android.content.Context
import androidx.preference.*

class ZeroPreferenceHelper(
    val context: Context,
    val screen: PreferenceScreen,
    initBlock: ZeroPreferenceHelper.() -> Unit
) {
    init {
        initBlock.invoke(this)
    }

    fun ZeroPreferenceNormal(
        title: String,
        summary: String,
        onClickListener: (Preference) -> Unit
    ) = Preference(context).apply {
        this.title = title
        this.summary = summary
        setOnPreferenceClickListener {
            onClickListener.invoke(it)
            true
        }
    }

    fun ZeroPreferenceList(
        key: String,
        title: String,
        summary: String,
        items: List<SettingList.SettingListEnum>
    ) = ListPreference(context).apply {
        this.key = key
        this.title = title
        this.summary = summary

        this.entries = items.map { it.title }.toTypedArray()
        this.entryValues = items.map { (it as Enum<*>).ordinal.toString() }.toTypedArray()
    }

    fun ZeroPreferenceCategory(
        title: String,
        vararg preferences: Preference
    ) {
        val category = PreferenceCategory(context)
        category.title = title
        screen.addPreference(category)
        preferences.forEach { category.addPreference(it) }
    }

    fun ZeroPreferenceSeekbar(keyString: String,titleString: String,summaryString:String, minValue: Int, maxValue: Int) =
        SeekBarPreference(context).apply {
            key = keyString
            title = titleString
            summary = summaryString
            min = minValue
            max = maxValue
        }
}
