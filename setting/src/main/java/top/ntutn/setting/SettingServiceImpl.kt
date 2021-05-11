package top.ntutn.setting

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.auto.service.AutoService
import top.ntutn.commonutil.AppUtil

@AutoService(SettingService::class)
class SettingServiceImpl : SettingService {
    override fun openSettingActivity(context: Context) {
        SettingsActivity.actionStart(context)
    }

    override fun getIntSetting(key: String, default: Int): Int {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(AppUtil.getApplicationContext())
        return sharedPreferences.getInt(key, default)
    }

    override fun getStringSetting(key: String, default: String): String {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(AppUtil.getApplicationContext())
        return sharedPreferences.getString(key, default) ?: default
    }

    override fun openAboutActivity(context: Context) {
        AboutActivity.actionStart(context)
    }
}