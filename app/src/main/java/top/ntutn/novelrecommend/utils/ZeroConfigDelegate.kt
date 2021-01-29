package top.ntutn.novelrecommend.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import top.ntutn.libzeroconfig.ZeroConfig
import kotlin.reflect.KProperty

object ZeroConfigHelper {
    private lateinit var gson: Gson
    private lateinit var sp: SharedPreferences
    private lateinit var bufferMap: MutableMap<Class<*>, Any?>

    private fun getKeyOfClass(clazz: Class<*>): String {
        val configAnnotation = clazz.getAnnotation(ZeroConfig::class.java)
        return configAnnotation!!.value
    }

    fun init(context: Context) {
        gson = Gson()
        sp = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        bufferMap = mutableMapOf()
    }

    /**
     * 获取所有已经定义的配置
     * 相当耗时
     */
    fun getAllDefinedConfigs(): List<Class<*>> {
        val classList = ClassUtil.getAllClassByPackageName(Package.getPackage("top.ntutn"))
        return AnnotationUtil.validAnnotation(classList, ZeroConfig::class.java)
        TODO("kapt")
    }

    fun fetchConfigFromNetwork() {
        //TODO 从互联网更新配置
    }

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

class ZeroConfigDelegate<T>(private val clazz: Class<T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? =
        ZeroConfigHelper.readConfig(clazz)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) =
        ZeroConfigHelper.saveConfig(clazz, value)
}

/**
 * 委托获取配置值
 * 泛型实化，调用更方便
 * @param clazz 配置类型
 */
fun <T> zeroConfig(clazz: Class<T>): ZeroConfigDelegate<T> = ZeroConfigDelegate(clazz)

/**
 * 委托获取配置值
 * 泛型实化，调用更方便
 */
inline fun <reified T> zeroConfig(): ZeroConfigDelegate<T> =
    zeroConfig(T::class.java)
