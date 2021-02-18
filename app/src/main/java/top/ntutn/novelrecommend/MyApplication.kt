package top.ntutn.novelrecommend

import android.app.Application
import android.content.Context
import android.os.Looper
import com.facebook.drawee.backends.pipeline.Fresco
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import top.ntutn.commonutil.AppUtil
import top.ntutn.commonutil.MetricsUtil
import top.ntutn.novelrecommend.utils.CrashReportingTree
import top.ntutn.novelrecommend.utils.TimeUtil
import top.ntutn.zeroconfigutil.ZeroConfigHelper

class MyApplication : Application() {
    private val isMainThread = Looper.getMainLooper() == Looper.myLooper()

    override fun onCreate() {
        super.onCreate()

        // 日志工具
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        // App工具
        AppUtil.init(applicationContext)

        Fresco.initialize(applicationContext)

        //ARouter
//        if (BuildConfig.DEBUG) {
//            ARouter.openLog()     // 打印日志
//            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//        ARouter.init(this)

        ZeroConfigHelper.init(applicationContext)
            .addConfigHolder(top.ntutn.zeroconfigutil.ZeroConfigHolder())
            .addConfigHolder(ZeroConfigHolder())
            .addConfigHolder(top.ntutn.commonutil.ZeroConfigHolder())

        //埋点工具，要在配置工具之后init
        MetricsUtil.init(applicationContext)

        EventBus.builder().addIndex(AppEventBusAppIndex()).installDefaultEventBus()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if(isMainThread) {
            TimeUtil.beginTimeCalculate(TimeUtil.COLD_START)
        }
    }
}
