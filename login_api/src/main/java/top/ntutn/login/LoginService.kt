package top.ntutn.login

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
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
     * 获取登录用户，若用户未登录，则拉起登录界面。结果在block中回调（不保证登录成功）
     */
    @Deprecated("不能达到预期效果")
    fun requireLoginUser(context: Context, block: (LoggedInUser?) -> Unit)

    fun startLoginActivity(activity: Activity, requestCode: Int)

    fun startLoginActivity(fragment: Fragment, requestCode: Int)

    /**
     * 退出登录
     */
    fun logout()

    /**
     * 刷新用户登录信息
     */
    fun refresh(block: (LoggedInUser?) -> Unit)
}