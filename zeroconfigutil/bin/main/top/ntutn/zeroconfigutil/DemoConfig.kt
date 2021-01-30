package top.ntutn.zeroconfigutil

import top.ntutn.libzeroconfig.ZeroConfig

@ZeroConfig("demo_config",owner = "liuhaixin.zero")
data class DemoConfig(val author: String = "liuhaixin.zero")
