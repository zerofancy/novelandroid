package top.ntutn.novelrecommend.model

import top.ntutn.libzeroconfig.ZeroConfigInformation

data class DebugConfigListModel(
    val type: Int, //0 配置项 1 scope分组
    val title: String? = null,
    val configInformation: ZeroConfigInformation? = null // 给配置项准备的
){
    companion object {
        const val TYPE_CONFIG_ITEM = 0
        const val TYPE_SCOPE = 1
    }
}
