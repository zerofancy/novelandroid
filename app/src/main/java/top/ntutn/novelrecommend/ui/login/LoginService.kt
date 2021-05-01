package top.ntutn.novelrecommend.ui.login

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import top.ntutn.novelrecommend.data.OperationResult

interface LoginService {
    @POST("login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<OperationResult<Any>>

    @POST("logout")
    fun logout(): Call<OperationResult<Any>>
}