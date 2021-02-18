package top.ntutn.commonutil

import android.content.Context
import com.ccj.client.android.analytics.JJEvent
import com.ccj.client.android.analytics.JJEventManager
import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.zeroconfigutil.zeroConfig

object MetricsUtil {
    fun init(context: Context) {
        val metricsConfig by zeroConfig<MetricsConfig>()

        val builder = JJEventManager.Builder(context)

        builder.setPushUrl(metricsConfig?.pushUrl ?: DEFAULT_PUSH_URL)
            .setDebug(BuildConfig.DEBUG)
            .setSidPeriodMinutes(
                metricsConfig?.sidPeriodMinutes ?: DEFAULT_PERIOD_MINUTES
            ) //sid改变周期
            .setPushLimitMinutes(
                metricsConfig?.pushLimitMinutes ?: DEFAULT_PUSH_LIMIT_MINUTES
            ) //多少分钟 push一次
            .setPushLimitNum(metricsConfig?.pushLimitNum ?: DEFAULT_PUSH_LIMIT_NUM) //多少条 就主动进行push
            .start() //开始*/
    }

    /**
     * 记录事件
     * 我认为没有必要在客户端区分具体是什么类别的事件
     * @param eventName 事件名
     * @param data 自定义参数
     */
    fun onEvent(eventName: String, data: Map<String, String> = mapOf()) {
        JJEvent.event(eventName, data)
    }

    /**
     * 主动推送
     */
    fun push() {
        JJEventManager.pushEvent()
    }

    const val DEFAULT_PUSH_URL = "http://10.78.207.28:8080/api"
    const val DEFAULT_PERIOD_MINUTES = 15
    const val DEFAULT_PUSH_LIMIT_MINUTES = 1.0
    const val DEFAULT_PUSH_LIMIT_NUM = 100
}

@ZeroConfig(key = "metrics_config", title = "埋点配置", owner = "liuhaixin.zero")
data class MetricsConfig(
    val pushUrl: String = MetricsUtil.DEFAULT_PUSH_URL,
    val sidPeriodMinutes: Int = MetricsUtil.DEFAULT_PERIOD_MINUTES,
    val pushLimitMinutes: Double = MetricsUtil.DEFAULT_PUSH_LIMIT_MINUTES,
    val pushLimitNum: Int = MetricsUtil.DEFAULT_PUSH_LIMIT_NUM
)