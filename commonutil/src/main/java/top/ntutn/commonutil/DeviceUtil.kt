package top.ntutn.commonutil

import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.content.edit
import timber.log.Timber
import java.util.*

/**
 * 获取唯一id
 */
object DeviceUtil {
    private val applicationContext: Context = AppUtil.getApplicationContext()
    private const val PREF_NAME = "DeviceInfo"
    private const val PREF_GUID = "GUID"

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
        sp.edit {
            putString(PREF_GUID, guid)
        }
        return guid
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