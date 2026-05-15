package com.chatglobal.data.network
import okhttp3.Interceptorimport okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
object RetrofitClient {
    private var token: String? = null
    fun setToken(t: String) { token = t }
    private val authInterceptor = Interceptor { chain ->
        val req = chain.request().newBuilder()
        token?.let { req.addHeader("Authorization", "Bearer $it") }
        chain.proceed(req.build())
    }
    private val client = OkHttpClient.Builder().addInterceptor(authInterceptor).connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build()
    val api: ApiService by lazy { Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java) }
}
