package top.ntutn.commonutil

import android.content.Context
import android.provider.Settings
import java.util.*

/**
 * 获取唯一id
 */
object DeviceUtil {
    private val applicationContext by lazy { AppUtil.getApplicationContext() }
    private const val PREF_NAME = "DeviceInfo"
    private const val PREF_GUID = "GUID"

    var uid: Long? = null

    fun getIMEI(): String? = null // 好像只有系统应用能读了

    fun getAndroidId(): String? {
        val androidId = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        // 这个id是假id https://www.cnblogs.com/lqminn/p/4204855.html
        if (androidId == "9774d56d682e549c") return null
        return androidId
    }

    fun getGUID(): String {
        val sp = applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var guid = sp.getString(PREF_GUID, null)
        if (guid != null) return guid
        guid = UUID.randomUUID().toString()
        sp.edit().apply {
            putString(PREF_GUID, guid)
            apply()
        }
        return guid
    }

    fun getDid(): String {
        val originId = getIMEI() ?: getAndroidId()
        return originId?.toByteArray()?.let { UUID.nameUUIDFromBytes(it) }?.toString() ?: getGUID()
    }

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