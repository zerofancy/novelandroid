package top.ntutn.novelrecommend

import android.app.Application
import android.content.Context
import timber.log.Timber
import top.ntutn.novelrecommend.utils.AppUtil
import top.ntutn.novelrecommend.utils.CrashReportingTree
import top.ntutn.novelrecommend.utils.ZeroConfigHelper

class MyApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
        ZeroConfigHelper.init(context)
        AppUtil.init()
    }
}