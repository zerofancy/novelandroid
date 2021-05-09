package top.ntutn.setting

import android.content.Context
import com.google.auto.service.AutoService

@AutoService(SettingService::class)
class SettingServiceImpl : SettingService {
    override fun openSettingActivity(context: Context) {
        SettingsActivity.actionStart(context)
    }

    override fun getIntSetting(key: String): Int {
        TODO("Not yet implemented")
    }

    override fun getStringSetting(key: String): String {
        TODO("Not yet implemented")
    }
}