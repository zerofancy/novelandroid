package top.ntutn.setting

import android.content.Context
import java.util.*

interface SettingService {
    companion object {
        // 来回传递这两个变量太麻烦了，还是直接set进来吧
        var appNameHolder = "appname"
        var versionNameHolder = "versionName"

        fun getInstance() = ServiceLoader.load(SettingService::class.java).firstOrNull()
    }

    fun openSettingActivity(context: Context)

    fun getIntSetting(key: String, default: Int = 0): Int

    fun getStringSetting(key: String, default: String = ""): String

    fun openAboutActivity(context: Context)
}