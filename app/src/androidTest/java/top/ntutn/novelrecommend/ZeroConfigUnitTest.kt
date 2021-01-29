package top.ntutn.novelrecommend

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.annotations.SerializedName
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.novelrecommend.utils.zeroConfig

/**
 * 测试字段，要求所有字段都有默认值
 */
@ZeroConfig("demo")
data class DemoConfig(
    val name: String? = null,
    val age: Int? = null,
    val married: Boolean? = null,
    @SerializedName("test") val joke: String? = "默认" // 后面优化可以考虑自己实现一个序列化名的注解
)

@RunWith(AndroidJUnit4::class)
class ZeroConfigUnitTest {
    @Test
    fun basicWrite() {
        var demoConfig by zeroConfig<DemoConfig>()
        demoConfig = DemoConfig("John", 23, false, "这真是一个悲伤的故事")
        Assert.assertEquals(DemoConfig("John", 23, false, "这真是一个悲伤的故事"), demoConfig)
    }

    @Test
    fun defaultValueTest() {
        var demoConfig by zeroConfig<DemoConfig>()
        demoConfig = DemoConfig("John", 23, false)
        Assert.assertEquals(DemoConfig("John", 23, false, "默认"), demoConfig)
    }
}