package top.ntutn.commonutil

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.zeroconfigutil.zeroConfig
import java.io.IOException

//https://www.jianshu.com/p/82fc13810e22
/**
 * @author : jc.lu
 * @create : 17/07/07.
 */
class ReceivedCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            val config: SharedPreferences.Editor = AppUtil.getApplicationContext()
                .getSharedPreferences("config", Context.MODE_PRIVATE)
                .edit()
            config.putStringSet("cookie", cookies)
            config.apply()
        }
        return originalResponse
    }
}

/**
 * @author : jc.lu
 * @create : 17/07/07.
 */
class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val preferences = AppUtil.getApplicationContext().getSharedPreferences(
            "config",
            Context.MODE_PRIVATE
        ).getStringSet("cookie", null)
        if (preferences != null) {
            for (cookie in preferences) {
                builder.addHeader("Cookie", cookie)
                Timber.v("Adding Header: $cookie")
            }
        }
        return chain.proceed(builder.build())
    }
}

object RetrofitUtil {
    internal const val BASE_URL = "http://8.141.64.84:8080/"
    private val retrofitConfig by zeroConfig<RetrofitConfig>()

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalHttpUrl = originalRequest.url()
                val url = originalHttpUrl.newBuilder()
//                    .addQueryParameter("deviceInfo", deviceInfo.toString())
                    .addQueryParameter("did", DeviceUtil.getDid())
                    .addQueryParameter("uid", TODO("获取uid"))
                    .build()
                val request = originalRequest.newBuilder()
                    .url(url)
                    .method(originalRequest.method(), originalRequest.body())
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .addInterceptor(ReceivedCookiesInterceptor()) // 持久化并使用保存cookie
            .addInterceptor(AddCookiesInterceptor())
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
