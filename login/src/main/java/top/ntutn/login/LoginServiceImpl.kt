package top.ntutn.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.google.auto.service.AutoService

@AutoService(LoginService::class)
class LoginServiceImpl : LoginService {
    override fun getCurrentLoginUser(): LoggedInUser? {
        TODO("Not yet implemented")
    }

    override fun requireLoginUser(context: Context, block: (LoggedInUser?) -> Unit) {
        val activity = LoginActivity()
        val registry = activity.activityResultRegistry.register(
            javaClass.canonicalName!!,
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                block.invoke(TODO())
            }
        }
        registry.launch(Intent())

//        registry.unregister()

        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}

class LoginActivity : FragmentActivity()