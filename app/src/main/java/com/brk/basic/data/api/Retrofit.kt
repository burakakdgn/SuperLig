package com.brk.basic.data.api

import com.brk.basic.constants.Global
import com.facebook.internal.Utility.currentLocale
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null){
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(100 , TimeUnit.SECONDS)

            okHttpClient.addInterceptor{
                val original: Request = it.request()
                val language = if (currentLocale.toLanguageTag() == "en") {
                    "en" // Set header to English
                } else {
                    "tr" // Set header to Turkish (default)
                }
                val request = original.newBuilder()
                    .addHeader("x-rapidapi-key", "YOUR API KEY")
                    .addHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                    .header("Accept-Language", language)
                    .method(original.method(), original.body())
                    .build()
                it.proceed(request)
            }
            val client = okHttpClient.build()

            val gson = GsonBuilder()
                .setLenient()
                .create()
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(Global.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        }
        return retrofit as Retrofit
    }
}