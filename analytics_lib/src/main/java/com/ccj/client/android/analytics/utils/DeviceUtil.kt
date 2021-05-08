package com.ccj.client.android.analytics.utils

import android.content.Context
import com.ccj.client.android.analytics.JJEventManager

/**
 * 获取唯一id
 */
object DeviceUtil {
    private val applicationContext: Context = JJEventManager.getContext()
    private const val PREF_NAME = "DeviceInfo"
    private const val PREF_GUID = "GUID"

    var uid: Long? = null

    fun getIMEI(): String? = top.ntutn.commonutil.DeviceUtil.getIMEI()

    fun getAndroidId(): String? = top.ntutn.commonutil.DeviceUtil.getAndroidId()

    fun getGUID(): String = top.ntutn.commonutil.DeviceUtil.getGUID()

    fun getDid(): String = top.ntutn.commonutil.DeviceUtil.getDid()

    fun getDeviceInfo() = DeviceInfo(
        imei = getIMEI(),
        androidId = getAndroidId(),
        guid = getGUID()
    )

    fun getDeviceInfoMap(): Map<String, String> = mutableMapOf<String, String>().apply {
        getIMEI()?.let { put("imei", it) }
        getAndroidId()?.let { put("android_id", it) }
        put("guid", getGUID())
    }
}

data class DeviceInfo(val imei: String?, val androidId: String?, val guid: String?)