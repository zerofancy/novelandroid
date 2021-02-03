package top.ntutn.novelrecommend.ui.base

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import top.ntutn.novelrecommend.utils.AppUtil
import top.ntutn.novelrecommend.utils.EventBusWrapper

open class BaseActivity : AppCompatActivity() {
    /**
     * Activity创建时调用
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("${javaClass.simpleName} onCreate()")
        val filter = IntentFilter()
        filter.addAction(AppUtil.BROADCAST_FINISH_ALL_ACTIVITIES)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                unregisterReceiver(this)
                (context as Activity).finish()
            }
        }, filter)
    }

    /**
     * Activity由不可见变为可见时调用
     */
    override fun onStart() {
        super.onStart()
        Timber.v("${javaClass.simpleName} onStart()")
        EventBusWrapper.register(this)
    }

    /**
     * Activity准备好与用户交互
     */
    override fun onResume() {
        super.onResume()
        Timber.v("${javaClass.simpleName} onResume()")
    }

    /**
     * 离开Activity
     */
    override fun onPause() {
        super.onPause()
        Timber.v("${javaClass.simpleName} onPause()")
    }

    /**
     * Activity完全不可见
     */
    override fun onStop() {
        super.onStop()
        Timber.v("${javaClass.simpleName} onStop()")
        EventBusWrapper.unregister(this)
    }

    /**
     * Activity被销毁
     */
    override fun onDestroy() {
        super.onDestroy()
        Timber.v("${javaClass.simpleName} onDestroy()")
    }

    /**
     * Activity被重新启动
     */
    override fun onRestart() {
        super.onRestart()
        Timber.v("${javaClass.simpleName} onRestart()")
    }
}