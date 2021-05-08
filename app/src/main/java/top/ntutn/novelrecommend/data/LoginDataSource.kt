package top.ntutn.novelrecommend.data

import retrofit2.await
import top.ntutn.login.LoggedInUser
import top.ntutn.novelrecommend.ui.login.LoginRepo
import top.ntutn.novelrecommend.utils.RetrofitUtil
import java.io.IOException
import top.ntutn.login.Result

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {

        return try {
            RetrofitUtil.create<LoginRepo>()
                .login(username, password)
                .await()
                .let {
                    if (it.code != 0) {
                        throw RuntimeException(it.message)
                    }
                }
            RetrofitUtil.create<LoginRepo>()
                .getUserInfo()
                .await()
                .let {
                    it ?: throw RuntimeException("获取用户信息失败")
                    Result.Success(it)
                }
        } catch (e: Exception) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun getUserInfo(): Result<LoggedInUser> {
        return try {
            RetrofitUtil.create<LoginRepo>()
                .getUserInfo()
                .await()
                .let {
                    it ?: throw RuntimeException("获取用户信息失败")
                    Result.Success(it)
                }
        } catch (e: Exception) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun logout() {
        RetrofitUtil.create<LoginRepo>()
            .logout()
            .await()
    }
}