package com.flatstack.mytddapplication.api.interceptors

import com.flatstack.mytddapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {
    companion object {
        const val API_KEY_NAME: String = "apikey"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().run {
            url().newBuilder().addQueryParameter(API_KEY_NAME, BuildConfig.OMDB_API_KEY).build().let {
                return chain.proceed(newBuilder().url(it).build())
            }
        }
}
