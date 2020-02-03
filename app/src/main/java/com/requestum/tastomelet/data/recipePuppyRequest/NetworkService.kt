package com.requestum.tastomelet.data.recipePuppyRequest

import com.requestum.tastomelet.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object NetworkService {
    private lateinit var mRetrofit: Retrofit

    val recipePuppyApi: RecipePuppyApi by lazy {
        mRetrofit.create<RecipePuppyApi>(
            RecipePuppyApi::class.java
        )
    }
    private const val serverUrl: String = "http://anarion.freedomain.thehost.com.ua/++++++"

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        mRetrofit = Retrofit.Builder()
            .baseUrl(serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

}