package top.ntutn.novelrecommend.utils

import android.content.Intent
import top.ntutn.novelrecommend.MyApplication


object AppUtil {
    fun getApplicationContext() = MyApplication.context

    fun finishAllActivities() {
        val intent = Intent()
        intent.action = BROADCAST_FINISH_ALL_ACTIVITIES
        getApplicationContext().sendBroadcast(intent)
    }

    fun init() {
        BROADCAST_FINISH_ALL_ACTIVITIES = "${this.javaClass.`package`!!.name}.finishAll"
    }

    lateinit var BROADCAST_FINISH_ALL_ACTIVITIES: String
}