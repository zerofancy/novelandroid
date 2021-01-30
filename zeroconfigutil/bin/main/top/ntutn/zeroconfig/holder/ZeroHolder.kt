package top.ntutn.zeroconfig.holder

import top.ntutn.libzeroconfig.ZeroConfigInformation

public object ZeroHolder {
    public val value: Map<String, ZeroConfigInformation> = mapOf("demo_config" to ZeroConfigInformation(
    key = "demo_config",
    title = "",
    clazz = "top.ntutn.zeroconfigutil.DemoConfig",
    scope = "top.ntutn.libzeroconfig.DefaultScope",
    owner = "liuhaixin.zero"
  ), "hi" to ZeroConfigInformation(
    key = "hi",
    title = "",
    clazz = "top.ntutn.novelrecommend.TestConfig",
    scope = "top.ntutn.libzeroconfig.DefaultScope",
    owner = "sgxdvb"
  ))
}