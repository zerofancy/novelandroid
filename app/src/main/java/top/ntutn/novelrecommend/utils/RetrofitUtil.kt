package top.ntutn.novelrecommend.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.ntutn.commonutil.DeviceUtil
import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.zeroconfigutil.zeroConfig

object RetrofitUtil {
    internal const val BASE_URL = "https://ntutn.top/novel/"
    private val retrofitConfig by zeroConfig<RetrofitConfig>()
    private val deviceInfo by lazy { DeviceUtil.getDeviceInfo() }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalHttpUrl = originalRequest.url()
                val url = originalHttpUrl.newBuilder()
//                    .addQueryParameter("deviceInfo", deviceInfo.toString())
                    .build()
                val request = originalRequest.newBuilder()
                    .url(url)
                    .method(originalRequest.method(), originalRequest.body())
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(retrofitConfig?.baseUrl ?: BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}

@ZeroConfig(key = "retrofit_config", title = "Retrofit配置", owner = "liuhaixin.zero")
data class RetrofitConfig(val baseUrl: String = RetrofitUtil.BASE_URL)
