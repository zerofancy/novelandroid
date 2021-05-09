package top.ntutn.setting

import android.content.Context
import java.util.*

interface SettingService {
    companion object {
        fun getInstance() = ServiceLoader.load(SettingService::class.java).firstOrNull()
    }

    fun openSettingActivity(context: Context, appName: String, versionName: String)

    fun getIntSetting(key: String): Int

    fun getStringSetting(key: String): String

    fun openAboutActivity(context: Context, appName: String, versionName: String)
}