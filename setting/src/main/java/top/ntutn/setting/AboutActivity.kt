package top.ntutn.setting

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.*

class AboutActivity : AbsAboutActivity() {
    companion object {
        const val MIT = "MIT License"
        const val APACHE_2 = "Apache Software License 2.0"
        const val GPL_V3 = "GNU general public license Version 3"

        const val EXTRA_APP_NAME = "app_name"
        const val EXTRA_VERSION_NAME = "version_name"

        fun actionStart(context: Context) {
            val intent = Intent(context, AboutActivity::class.java).apply {
                putExtra(EXTRA_APP_NAME, SettingService.appNameHolder)
                putExtra(EXTRA_VERSION_NAME, SettingService.versionNameHolder)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.drawable.ic_launcher_treasure)
        title = intent.getStringExtra(EXTRA_APP_NAME)
        slogan.text = getString(R.string.slogan)
        version.text = "v${intent.getStringExtra(EXTRA_VERSION_NAME)}"
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.apply {
            add(Category("介绍与帮助"))
            add(Card(getText(R.string.description)))

            add(Category("Developer"))
            add(Contributor(R.mipmap.avatar, "刘海鑫", "Developer & designer", "https://ntutn.top"))

            add(Category("Open Source Licenses"))
            add(
                License(
                    "about-page",
                    "drakeet",
                    APACHE_2,
                    "https://github.com/drakeet/about-page"
                )
            )
            add(
                License(
                    "AnimLogoView",
                    "seagazer",
                    APACHE_2,
                    "https://github.com/seagazer/animlogoview"
                )
            )
//            add(
//                License(
//                    "ARouter",
//                    "阿里巴巴",
//                    APACHE_2,
//                    "https://github.com/alibaba/ARouter/tree/master"
//                )
//            )
            add(
                License(
                    "AutoService",
                    "Google",
                    APACHE_2,
                    "https://github.com/google/auto/tree/master/service"
                )
            )
            add(
                License(
                    "EventBus",
                    "greenrobot",
                    APACHE_2,
                    "https://github.com/greenrobot/EventBus"
                )
            )
            add(
                License(
                    "Fresco",
                    "facebook",
                    MIT,
                    "https://github.com/facebook/fresco"
                )
            )
            add(License("Gson", "Google", APACHE_2, "https://github.com/google/gson"))
            add(License("IconPark", "字节跳动", APACHE_2, "https://github.com/bytedance/IconPark"))
            add(License("JJEvent", "ccj659", APACHE_2, "https://github.com/ccj659/JJEvent"))
            add(License("KotlinPoet", "square", APACHE_2, "https://github.com/square/kotlinpoet"))
            add(
                License(
                    "LayoutManagerGroup",
                    "钉某人",
                    APACHE_2,
                    "https://github.com/DingMouRen/LayoutManagerGroup"
                )
            )
            add(
                License(
                    "LikeView",
                    "jaren",
                    APACHE_2,
                    "https://github.com/qkxyjren/LikeView"
                )
            )
            add(
                License(
                    "Material Components for Android",
                    "Google",
                    APACHE_2,
                    "https://github.com/material-components/material-components-android"
                )
            )
            add(
                License(
                    "Retrofit2",
                    "Square",
                    APACHE_2,
                    "https://square.github.io/retrofit/"
                )
            )
            add(
                License(
                    "Timber",
                    "Jake Wharton",
                    APACHE_2,
                    "https://github.com/JakeWharton/timber"
                )
            )
        }
    }
}