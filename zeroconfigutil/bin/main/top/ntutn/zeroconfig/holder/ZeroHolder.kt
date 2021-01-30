package top.ntutn.zeroconfig.holder

import top.ntutn.libzeroconfig.ZeroConfigInformation

public object ZeroHolder {
    public val value: Map<String, ZeroConfigInformation> = mapOf("demo_config" to ZeroConfigInformation(
    key = "demo_config",
    title = "",
    clazz = top.ntutn.zeroconfigutil.DemoConfig::class,
    scope = top.ntutn.libzeroconfig.DefaultScope::class,
    owner = "liuhaixin.zero"
  ), "hi" to ZeroConfigInformation(
    key = "hi",
    title = "",
    clazz = top.ntutn.novelrecommend.TestConfig::class,
    scope = top.ntutn.libzeroconfig.DefaultScope::class,
    owner = "sgxdvb"
  ))
}