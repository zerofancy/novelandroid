package top.ntutn.novelrecommend.data

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import top.ntutn.commonutil.AppUtil
import top.ntutn.novelrecommend.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

object LoginRepository {
    private const val SP_FILE = "current_user"
    private const val SP_KEY_USER_JSON = "user_json"

    private val dataSource = LoginDataSource()
    private val gson by lazy { Gson() }

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
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

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }


    suspend fun refresh() {
        TODO("用于定时刷新用户")
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        AppUtil.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE).edit {
            putString(SP_KEY_USER_JSON, gson.toJson(loggedInUser))
        }
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}