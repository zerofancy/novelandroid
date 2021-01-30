package top.ntutn.libzeroconfig

import kotlin.reflect.KClass

/**
 * 自动生成代码中的list item
 */
data class ZeroConfigInformation(
    val key: String,
    val title: String,
    val clazz: String,
    val scope: String,
    val owner: String
)