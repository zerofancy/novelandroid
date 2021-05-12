package top.ntutn.novelrecommend

import android.content.Context
import com.google.auto.service.AutoService
import top.ntutn.novelrecommend.ui.activity.DebugHelperActivity
import top.ntutn.setting.DebugService

@AutoService(DebugService::class)
class DebugServiceImpl : DebugService {
    override fun openDebugActivity(context: Context) {
        DebugHelperActivity.actionStart(context)
    }
}