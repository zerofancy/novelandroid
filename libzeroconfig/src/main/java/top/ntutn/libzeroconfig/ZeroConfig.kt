package top.ntutn.libzeroconfig

/**
 * 标注于配置实体类之上
 * @param value 配置字段名
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ZeroConfig(val value: String)