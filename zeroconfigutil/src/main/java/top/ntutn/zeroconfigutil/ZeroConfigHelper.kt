package top.ntutn.zeroconfigutil

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import top.ntutn.libzeroconfig.IZeroConfigHolder
import top.ntutn.libzeroconfig.ZeroConfigInformation

object ZeroConfigHelper {
    private lateinit var gson: Gson
    private lateinit var sp: SharedPreferences
    private lateinit var bufferMap: MutableMap<Class<*>, Any?>
    private var configs: MutableMap<String, ZeroConfigInformation> = mutableMapOf()

    private fun getKeyOfClass(clazz: Class<*>): String {
        return configs.filter { it.value.clazz == clazz.canonicalName }.keys.first()
    }

    fun addConfigHolder(configHolder: IZeroConfigHolder): ZeroConfigHelper {
        configs.putAll(configHolder.getValue())
        return this
    }

    fun init(context: Context): ZeroConfigHelper {
        gson = Gson()
        sp = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        bufferMap = mutableMapOf()
        return this
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

    private fun getClassByKey(key: String): Class<*>? {
        val className = configs[key]?.clazz ?: return null
        return Class.forName(className)
    }

    fun readRawConfig(key: String): String? {
        val clazz = getClassByKey(key) ?: return null
        var rawString = sp.getString(getKeyOfClass(clazz), null)
        // 正确显示配置的默认值
        if (rawString == null) {
            rawString = gson.toJson(clazz.newInstance())
        }
        return rawString
    }

    @Throws(ClassNotFoundException::class)
    fun saveRawConfig(key: String, value: String) {
        val clazz = getClassByKey(key) ?: throw ClassNotFoundException("未找到配置项：$key")
        bufferMap.remove(clazz)
        sp.edit {
            putString(getKeyOfClass(clazz), value)
        }
    }

    fun removeConfig(clazz: Class<*>) {
        bufferMap.remove(clazz)
        sp.edit {
            remove(getKeyOfClass(clazz))
        }
    }

    private const val CONFIG_FILE_NAME = "zeroConfig"
}