package top.ntutn.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.auto.service.AutoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AutoService(LoginService::class)
class LoginServiceImpl : LoginService {
    override fun getCurrentLoginUser(): LoggedInUser? {
        return LoginRepository.user
    }

    override fun requireLoginUser(context: Context, block: (LoggedInUser?) -> Unit) {
        getCurrentLoginUser()?.let {
            block.invoke(it)
            return
        }
        // 不知道为什么找不到这个方法，可能是Android Studio的bug
        val activity = LoginActivity() as ComponentActivity
        val loginActivityLauncher =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                block.invoke(getCurrentLoginUser())
            }
        loginActivityLauncher.launch(Intent())
    }

    override fun startLoginActivity(activity: Activity, requestCode: Int) {
        LoginActivity.startForResult(activity, requestCode)
    }

    override fun startLoginActivity(fragment: Fragment, requestCode: Int) {
        LoginActivity.startForResult(fragment, requestCode)
    }

    override fun logout() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                LoginRepository.logout()
            }
        }
    }

    override fun refresh(block: (LoggedInUser?) -> Unit) {
        GlobalScope.launch {
            val result = withContext(Dispatchers.IO) {
                val result = LoginRepository.refresh()
                if (result is Result.Success) result.data else null
            }
            withContext(Dispatchers.Main) {
                block.invoke(result)
            }
        }
    }
}