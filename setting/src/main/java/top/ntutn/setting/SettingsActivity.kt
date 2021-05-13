package top.ntutn.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import top.ntutn.commonui.base.BaseActivity
import top.ntutn.setting.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity() {
    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val context = preferenceManager.context
            val screen = preferenceManager.createPreferenceScreen(context)

            ZeroPreferenceHelper(context, screen) {
                ZeroPreferenceCategory(
                    "阅读器",
                    ZeroPreferenceList(
                        SettingKey.FONT_TYPE,
                        "字体类型",
                        "修改阅读器使用的字体类型",
                        SettingList.FONT_TYPE
                    ),
                    ZeroPreferenceSeekbar(
                        SettingKey.FONT_SIZE,
                        "字号",
                        "设置阅读器的字号",
                        18, 30
                    )
                )
                ZeroPreferenceCategory(
                    "外观",
                    ZeroPreferenceList(
                        SettingKey.EYE_PROTECT,
                        "护眼模式",
                        "使用更柔和的光线展示",
                        SettingList.EYE_PROTECT
                    )
                )
                ZeroPreferenceCategory("关于",
                    ZeroPreferenceNormal("关于", "关于这个应用的小故事") {
                        AboutActivity.actionStart(requireContext())
                    }
                )
            }

            preferenceScreen = screen
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}