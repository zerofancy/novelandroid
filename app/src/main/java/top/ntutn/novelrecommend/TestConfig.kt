package top.ntutn.novelrecommend

import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.novelrecommend.utils.zeroConfig

@ZeroConfig("hi", owner = "sgxdvb")
data class TestConfig(val value: String = "")

fun test() {
    val testConfig by zeroConfig<TestConfig>()
}

