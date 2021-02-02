package top.ntutn.novelrecommend

import android.app.Application
import android.content.Context
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import top.ntutn.novelrecommend.utils.AppUtil
import top.ntutn.novelrecommend.utils.CrashReportingTree
import top.ntutn.zeroconfigutil.ZeroConfigHelper

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
        AppUtil.init()
        ZeroConfigHelper.init(applicationContext)
        ZeroConfigHelper.addConfigHolder(top.ntutn.zeroconfigutil.ZeroConfigHolder())
            .addConfigHolder(ZeroConfigHolder())
        EventBus.builder().addIndex(AppEventBusAppIndex()).installDefaultEventBus()
    }
}
