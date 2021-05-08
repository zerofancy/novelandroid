package com.smile.analytics

/**
 * 埋点是一个很高频的功能，所以还是不要重复创建对象了
 */
object MetricsServiceDelegate : MetricsService by MetricsService.getInstance()!!