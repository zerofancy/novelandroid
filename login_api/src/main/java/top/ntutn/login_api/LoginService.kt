package top.ntutn.login_api

import android.content.Context
import java.util.*

interface LoginService {
    companion object {
        fun getInstance() =
            ServiceLoader.load(LoginService::class.java, LoginService::class.java.classLoader)
                .toList().firstOrNull()
    }

    /**
     * 若用户已经登录，返回登录用户
     */
    fun getCurrentLoginUser(): LoggedInUser?

    /**
     * 获取登录用户，若用户未登录，则拉起登录界面
     */
    fun requireLoginUser(context: Context, block: (LoggedInUser?) -> Unit)

    /**
     * 退出登录
     */
    fun logout()
}