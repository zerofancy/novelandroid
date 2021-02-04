package top.ntutn.novelrecommend.ui.activity

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.*
import top.ntutn.novelrecommend.BuildConfig
import top.ntutn.novelrecommend.R

class AboutActivity : AbsAboutActivity() {
    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher_treasure)
        slogan.text = getString(R.string.slogan)
        version.text = "v${BuildConfig.VERSION_NAME}"
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.apply {
            add(Category("介绍与帮助"))
            add(Card(getText(R.string.description)))

            add(Category("Developer"))
            add(Contributor(R.drawable.avatar, "刘海鑫", "Developer & designer", "https://ntutn.top"))

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
            add(License("Gson", "Google", APACHE_2, "https://github.com/google/gson"))
            add(License("KotlinPoet", "square", APACHE_2, "https://github.com/square/kotlinpoet"))
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
                    "Timber",
                    "Jake Wharton",
                    APACHE_2,
                    "https://github.com/JakeWharton/timber"
                )
            )
        }
    }

    companion object {
        const val MIT = "MIT License"
        const val APACHE_2 = "Apache Software License 2.0"
        const val GPL_V3 = "GNU general public license Version 3"
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }
}