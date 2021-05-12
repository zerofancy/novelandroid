package top.ntutn.novelrecommend.ui.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import top.ntutn.commonui.base.EyeProtectFrameLayout
import top.ntutn.commonui.common.InitedLiveData
import top.ntutn.setting.SettingKey
import top.ntutn.setting.SettingList
import top.ntutn.setting.SettingServiceDelegate

/**
 * 用于MainActivity的状态绑定
 */
class MainViewModel : ViewModel() {
    private val _eyeProtectMode = InitedLiveData { EyeProtectFrameLayout.EyeProtectColor.NONE }
    val eyeProtectMode: LiveData<EyeProtectFrameLayout.EyeProtectColor> = _eyeProtectMode

    fun updateEyeProtectMode() {
        val eyeProtectSetting =
            SettingServiceDelegate.getStringSetting(SettingKey.EYE_PROTECT)
        val eyeProtectEnum = SettingList.EyeProtect.values()
            .getOrNull(eyeProtectSetting.toIntOrNull() ?: 0)
        val eyeProtectColor =
            when (eyeProtectEnum) {
                SettingList.EyeProtect.BROWN -> EyeProtectFrameLayout.EyeProtectColor.BROWN
                SettingList.EyeProtect.GREEN -> EyeProtectFrameLayout.EyeProtectColor.GREEN
                else -> EyeProtectFrameLayout.EyeProtectColor.NONE
            }
        _eyeProtectMode.value = eyeProtectColor
    }
}