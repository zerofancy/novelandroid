package com.ccj.client.android.analytics

import android.content.Context
import com.ccj.client.android.analyticlib.BuildConfig
import com.google.auto.service.AutoService
import com.smile.analytics.MetricsService

//FIXME 让这部分支持配置
@AutoService(MetricsService::class)
class MetricsServiceImpl : MetricsService {
    companion object {
        const val DEFAULT_PUSH_URL = "http://8.141.64.84:8080/api/tracking/report"
        const val DEFAULT_PERIOD_MINUTES = 15
        const val DEFAULT_PUSH_LIMIT_MINUTES = 1.0
        const val DEFAULT_PUSH_LIMIT_NUM = 100
    }

    override fun init(context: Context) {
        val builder = JJEventManager.Builder(context)

        builder.setPushUrl(DEFAULT_PUSH_URL)
            .setDebug(BuildConfig.DEBUG)
            .setSidPeriodMinutes(DEFAULT_PERIOD_MINUTES) //sid改变周期
            .setPushLimitMinutes(DEFAULT_PUSH_LIMIT_MINUTES) //多少分钟 push一次
            .setPushLimitNum(DEFAULT_PUSH_LIMIT_NUM) //多少条 就主动进行push
            .start() //开始*/
    }

    override fun onEvent(eventName: String, data: Map<String, Any>) {
        JJEvent.event(eventName, data)
    }

    override fun push() {
        JJEventManager.pushEvent()
    }
}