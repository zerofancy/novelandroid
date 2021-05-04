package top.ntutn.novelrecommend.data

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import top.ntutn.commonutil.AppUtil
import top.ntutn.commonutil.LoggedInUser
import top.ntutn.commonutil.LoggedInUserHolder

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

object LoginRepository {
    private const val SP_FILE = "current_user"
    private const val SP_KEY_USER_JSON = "user_json"

    private val dataSource = LoginDataSource()
    private val gson by lazy { Gson() }

    var lastRefreshTime = 0L
        private set

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set(value) {
            field = value
            LoggedInUserHolder.user = value
        }

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // FIXME 用户信息存储加密
        val sp = AppUtil.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE)
        val json = sp.getString(SP_KEY_USER_JSON, null)
        user = try {
            gson.fromJson(json, LoggedInUser::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)
        setLoggedInUser(result)
        return result
    }


    suspend fun refresh(): Result<LoggedInUser> {
        user?.let {
            // 避免重复刷新
            if (System.currentTimeMillis() - lastRefreshTime < 1000 * 60) {
                return Result.Success(it)
            }
        }
        val result = dataSource.getUserInfo()
        setLoggedInUser(result)
        return result
    }

    private fun setLoggedInUser(result: Result<LoggedInUser>) {
        this.user = if (result is Result.Success) result.data else null
        lastRefreshTime = System.currentTimeMillis()
        AppUtil.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE).edit {
            putString(SP_KEY_USER_JSON, gson.toJson(user))
        }
    }
}