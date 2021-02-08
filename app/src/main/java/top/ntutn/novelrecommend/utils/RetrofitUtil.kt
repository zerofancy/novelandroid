package top.ntutn.novelrecommend.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.zeroconfigutil.zeroConfig

object RetrofitUtil {
    internal const val BASE_URL = "https://ntutn.top/novel/"

    private val retrofitConfig by zeroConfig<RetrofitConfig>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(retrofitConfig?.baseUrl ?: BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}

@ZeroConfig(key = "retrofit_config", title = "Retrofit配置", owner = "liuhaixin.zero")
data class RetrofitConfig(val baseUrl: String = RetrofitUtil.BASE_URL)
