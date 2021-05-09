package top.ntutn.setting

import android.content.Context
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceScreen

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

    fun ZeroPreferenceCategory(
        title: String,
        vararg preferences: Preference
    ) {
        val category = PreferenceCategory(context)
        category.title = title
        screen.addPreference(category)
        preferences.forEach { category.addPreference(it) }
    }
}
