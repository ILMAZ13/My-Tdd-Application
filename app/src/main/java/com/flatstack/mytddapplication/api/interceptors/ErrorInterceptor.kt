package com.flatstack.mytddapplication.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        val stringBody = response.peekBody(Long.MAX_VALUE).string()
        if (response.code() == 200 && response.isSuccessful && stringBody.contains("{\"Response\":\"False\"")) {
            response = response.newBuilder().code(403).build()
        }
        return response
    }
}
