package top.ntutn.novelrecommend

import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.zeroconfigutil.zeroConfig

@ZeroConfig("hi", owner = "sgxdvb",title = "测试分组")
data class TestConfig(val value: String = "")

fun test() {
    val testConfig by zeroConfig<TestConfig>()
}

