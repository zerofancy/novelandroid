package top.ntutn.commonutil

import android.content.Context

/**
 * 获取唯一id
 */
object DeviceUtil {
    private val applicationContext: Context = AppUtil.getApplicationContext()
    private const val PREF_NAME = "DeviceInfo"
    private const val PREF_GUID = "GUID"

    fun getIMEI() = com.ccj.client.android.analytics.utils.DeviceUtil.getIMEI()

    fun getAndroidId() = com.ccj.client.android.analytics.utils.DeviceUtil.getAndroidId()

    fun getGUID() = com.ccj.client.android.analytics.utils.DeviceUtil.getGUID()

    fun getDid() = com.ccj.client.android.analytics.utils.DeviceUtil.getDid()

    fun getDeviceInfo() = DeviceInfo(
        imei = getIMEI(),
        androidId = getAndroidId(),
        guid = getGUID()
    )

    fun getDeviceInfoMap(): Map<String, String> =
        com.ccj.client.android.analytics.utils.DeviceUtil.getDeviceInfoMap()
}

data class DeviceInfo(val imei: String?, val androidId: String?, val guid: String?)