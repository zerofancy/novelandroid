package top.ntutn.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import top.ntutn.commonui.base.BaseActivity
import top.ntutn.commonutil.showLongToast
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
                ZeroPreferenceCategory("关于",
                    ZeroPreferenceNormal("关于", "关于这个应用的小故事") {
                        "打开关于界面".showLongToast()
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