package top.ntutn.zeroconfigutil

import top.ntutn.libzeroconfig.ZeroConfig

@ZeroConfig("demo_config",owner = "liuhaixin.zero",title = "在util中的测试分组")
data class DemoConfig(val author: String = "liuhaixin.zero")
