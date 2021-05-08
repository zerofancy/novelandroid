package com.smile.analytics

import android.content.Context
import java.util.*

interface MetricsService {
    companion object {
        fun getInstance(): MetricsService? {
            val services = ServiceLoader.load(
                MetricsService::class.java,
                MetricsService::class.java.classLoader
            )
            return services.firstOrNull()
        }
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