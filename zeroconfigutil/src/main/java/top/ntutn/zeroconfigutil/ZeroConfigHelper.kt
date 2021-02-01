package top.ntutn.zeroconfigutil

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import top.ntutn.libzeroconfig.IZeroConfigHolder
import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.libzeroconfig.ZeroConfigInformation

object ZeroConfigHelper {
    private lateinit var gson: Gson
    private lateinit var sp: SharedPreferences
    private lateinit var bufferMap: MutableMap<Class<*>, Any?>
    private var configs: MutableMap<String, ZeroConfigInformation> = mutableMapOf()

    private fun getKeyOfClass(clazz: Class<*>): String {
        val configAnnotation = clazz.getAnnotation(ZeroConfig::class.java)
        return configAnnotation!!.key
    }

    fun addConfigHolder(configHolder: IZeroConfigHolder): ZeroConfigHelper {
        configs.putAll(configHolder.getValue())
        return this
    }

    fun init(context: Context) {
        gson = Gson()
        sp = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        bufferMap = mutableMapOf()
    }

    /**
     * 获取所有已经定义的配置
     */
    fun getAllDefinedConfigs(): Collection<ZeroConfigInformation> = configs.values

    fun <T> saveConfig(clazz: Class<*>, value: T) {
        bufferMap[clazz] = value
        sp.edit {
            putString(getKeyOfClass(clazz), gson.toJson(value))
        }
    }

    fun <T> readConfig(clazz: Class<*>): T {
        return if (bufferMap.containsKey(clazz)) {
            bufferMap[clazz]
        } else {
            val jsonString = sp.getString(getKeyOfClass(clazz), "{}")
            gson.fromJson(jsonString, clazz)
        } as T
    }

    fun removeConfig(clazz: Class<*>) {
        bufferMap.remove(clazz)
        sp.edit {
            remove(getKeyOfClass(clazz))
        }
    }

    private const val CONFIG_FILE_NAME = "zeroConfig"
}