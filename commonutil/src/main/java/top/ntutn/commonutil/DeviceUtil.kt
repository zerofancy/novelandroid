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

    fun getIMEI(): String? {
        val manager =
            applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
                ?: return null
        return try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                manager.imei
            } else {
                manager.deviceId
            }
        } catch (e: Exception) {
            Timber.w(e)
            null
        }
    }

    fun getAndroidId(): String? {
        val androidId = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
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
}