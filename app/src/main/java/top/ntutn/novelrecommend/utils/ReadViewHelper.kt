package top.ntutn.novelrecommend.utils

import top.ntutn.readview.BreakReadView
import top.ntutn.setting.SettingKey
import top.ntutn.setting.SettingList
import top.ntutn.setting.SettingServiceDelegate

object ReadViewHelper {
    fun updateFontSetting(readView: BreakReadView) {
        val fontSize = SettingServiceDelegate.getIntSetting(SettingKey.FONT_SIZE, 18).toFloat()
        readView.changeTextSize(fontSize)
        val fontTypeSetting = SettingServiceDelegate.getStringSetting(SettingKey.FONT_TYPE)
        val fontTypeEnum = SettingList.FontType.values()
            .getOrNull(fontTypeSetting.toIntOrNull() ?: 0)
        val path = when (fontTypeEnum) {
            SettingList.FontType.FANGSONG -> "font/fangsong.ttf"
            SettingList.FontType.HEITI -> "font/heiti.ttf"
            SettingList.FontType.KAITI -> "font/kaiti.ttf"
            SettingList.FontType.SHUSONG -> "font/shusong.ttf"
            SettingList.FontType.YUANTI -> "font/yuanti.ttf"
            else -> "font/zhiyong.ttf"
        }
        readView.changeFont(path)
    }
}