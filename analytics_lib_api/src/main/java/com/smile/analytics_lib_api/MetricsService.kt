package com.smile.analytics_lib_api

import android.content.Context
import java.util.*

interface MetricsService {
    companion object {
        fun getInstance() =
            ServiceLoader.load(MetricsService::class.java, MetricsService::class.java.classLoader)
                .toList().firstOrNull()
    }

    /**
     * 埋点初始化
     */
    fun init(context: Context)

    /**
     * 记录事件
     */
    fun onEvent(eventName: String, data: Map<String, Any> = mapOf())

    /**
     * 主动推送
     */
    fun push()
}