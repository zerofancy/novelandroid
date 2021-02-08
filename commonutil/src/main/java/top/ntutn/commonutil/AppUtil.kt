package top.ntutn.commonutil

import android.content.Context
import android.content.Intent

object AppUtil {
    private lateinit var applicationContext: Context

    fun getApplicationContext() = applicationContext

    fun finishAllActivities() {
        val intent = Intent()
        intent.action = BROADCAST_FINISH_ALL_ACTIVITIES
        getApplicationContext().sendBroadcast(intent)
    }

    fun init(context: Context) {
        BROADCAST_FINISH_ALL_ACTIVITIES = "${this.javaClass.`package`!!.name}.finishAll"
        applicationContext = context
    }

    lateinit var BROADCAST_FINISH_ALL_ACTIVITIES: String
}