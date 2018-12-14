package com.flatstack.mytddapplication.di.modules

import com.flatstack.mytddapplication.api.interceptors.AuthIntercepor
import com.flatstack.mytddapplication.api.interceptors.LoggingInterceptor
import okhttp3.Interceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val netModule = Kodein.Module(name = "apiModule") {
    bind<Interceptor>(tag = "logging") with singleton { LoggingInterceptor() }
    bind<Interceptor>(tag = "auth") with singleton { AuthIntercepor() }
}
