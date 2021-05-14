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

    fun getStatusBarHeightCompat(context: Context): Int {
        var result = 0
        val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            result = context.resources.getDimensionPixelOffset(resId)
        }
        if (result <= 0) {
            result = context.resources.getDimensionPixelOffset(25.dp)
        }
        return result
    }

    lateinit var BROADCAST_FINISH_ALL_ACTIVITIES: String
}