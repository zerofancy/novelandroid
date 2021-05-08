package top.ntutn.login

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import top.ntutn.login.LoggedInUser
import top.ntutn.login.OperationResult

interface LoginRepo {
    @POST("login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("remember-me") rememberMe: Boolean = true
    ): Call<OperationResult<Any>>

    @GET("api/user/info")
    fun getUserInfo(): Call<LoggedInUser?>

    @POST("logout")
    fun logout(): Call<OperationResult<Any>>
}