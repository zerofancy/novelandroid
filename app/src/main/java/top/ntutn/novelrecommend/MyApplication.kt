package top.ntutn.novelrecommend

import android.app.Application
import android.content.Context
import top.ntutn.novelrecommend.utils.ZeroConfigHelper

class MyApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        ZeroConfigHelper.initZeroConfig(context)
    }
}