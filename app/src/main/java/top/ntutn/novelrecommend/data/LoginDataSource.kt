package top.ntutn.novelrecommend.data

import retrofit2.await
import top.ntutn.novelrecommend.data.model.LoggedInUser
import top.ntutn.novelrecommend.ui.login.LoginService
import top.ntutn.novelrecommend.utils.RetrofitUtil
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {

        return try {
            RetrofitUtil.create<LoginService>()
                .login(username, password)
                .await()
                .let {
                    if (it.code == 0) {
                        Result.Success(LoggedInUser("test_uid", "test_nickname"))
                    } else {
                        Result.Error(RuntimeException(it.message))
                    }
                }
        } catch (e: Exception) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun logout() {
        RetrofitUtil.create<LoginService>()
            .logout()
            .await()
    }
}