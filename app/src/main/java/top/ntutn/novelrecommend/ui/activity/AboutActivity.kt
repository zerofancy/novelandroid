package top.ntutn.novelrecommend.ui.activity

import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.AbsAboutActivity
import top.ntutn.novelrecommend.R

class AboutActivity:AbsAboutActivity() {
    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher_treasure)
        slogan.text = ""
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        TODO("Not yet implemented")
    }

}