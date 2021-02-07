package top.ntutn.novelrecommend

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import top.ntutn.novelrecommend.utils.AppUtil
import top.ntutn.novelrecommend.utils.CrashReportingTree
import top.ntutn.zeroconfigutil.ZeroConfigHelper

class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        // 日志工具
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        // App工具
        AppUtil.init()

        //ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)

        ZeroConfigHelper.init(applicationContext)
        ZeroConfigHelper.addConfigHolder(top.ntutn.zeroconfigutil.ZeroConfigHolder())
            .addConfigHolder(ZeroConfigHolder())

        EventBus.builder().addIndex(AppEventBusAppIndex()).installDefaultEventBus()
    }
}
