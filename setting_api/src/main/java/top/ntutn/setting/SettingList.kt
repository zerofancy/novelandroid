package top.ntutn.setting


object SettingList {
    interface SettingListEnum {
        val title: String
    }

    enum class EyeProtect(override val title: String) : SettingListEnum {
        NONE("无"),
        BROWN("深棕"),
        GREEN("墨绿")
    }

    enum class FontType(override val title: String) : SettingListEnum {
        FANGSONG("方正仿宋"),
        HEITI("方正黑体"),
        KAITI("方正楷体"),
        SHUSONG("方正书宋"),
        YUANTI("资源圆体"),
        SHOUXIE("志勇手写体")
    }

    val EYE_PROTECT = EyeProtect.values().toList()
    val FONT_TYPE = FontType.values().toList()
}