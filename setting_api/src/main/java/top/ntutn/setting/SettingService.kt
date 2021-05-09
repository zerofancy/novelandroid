package top.ntutn.setting

import android.content.Context
import java.util.*

interface SettingService {
    companion object {
        fun getInstance() = ServiceLoader.load(SettingService::class.java).firstOrNull()
    }

    fun openSettingActivity(context: Context)

    fun getIntSetting(key: String): Int

    fun getStringSetting(key: String): String
}