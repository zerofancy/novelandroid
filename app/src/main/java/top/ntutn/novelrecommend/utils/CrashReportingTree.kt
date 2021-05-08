package top.ntutn.novelrecommend.utils

import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority >= Log.ERROR && t != null) {
            // TODO("执行crash上报的逻辑")
        }
    }
}