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

    val EYE_PROTECT = EyeProtect.values().toList()
}