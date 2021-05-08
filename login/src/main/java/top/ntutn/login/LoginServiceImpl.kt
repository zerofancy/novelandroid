package top.ntutn.login

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.auto.service.AutoService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        val activity = LoginActivity() as ComponentActivity

        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            block.invoke(getCurrentLoginUser())
        }
    }

    override fun logout() {
        GlobalScope.launch {
            LoginRepository.logout()
        }
    }
}