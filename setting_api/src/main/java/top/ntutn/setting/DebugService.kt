package top.ntutn.setting

import android.content.Context
import java.util.*

object DebugServiceDelegate : DebugService by DebugService.getInstance()!!

//FIXME Debug部分组件下沉
interface DebugService {
    companion object {
        fun getInstance() = ServiceLoader.load(DebugService::class.java).firstOrNull()
    }

    fun openDebugActivity(context: Context)
}