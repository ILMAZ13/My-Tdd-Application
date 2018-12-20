package com.flatstack.mytddapplication.di.modules

import com.flatstack.mytddapplication.BuildConfig
import com.flatstack.mytddapplication.api.interceptors.AuthInterceptor
import com.flatstack.mytddapplication.api.interceptors.LoggingInterceptor
import com.flatstack.mytddapplication.api.services.MovieService
import com.flatstack.mytddapplication.api.util.LiveDataCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val netModule = Kodein.Module(name = "apiModule") {
    bind<Interceptor>(tag = "logging") with singleton { LoggingInterceptor() }
    bind<Interceptor>(tag = "auth") with singleton { AuthInterceptor() }

    bind<Gson>() with singleton { provideGson() }
    bind<OkHttpClient>() with singleton { provideOkHttpClient(instance(tag = "logging"), instance(tag = "auth")) }
    bind<Retrofit>() with singleton { provideRetrofit(instance(), instance()) }

    bind<MovieService>() with singleton { instance<Retrofit>().create(MovieService::class.java) }
}

private fun provideGson() =
    GsonBuilder()
        .setDateFormat("dd MMM yyyy")
        .create()

private fun provideOkHttpClient(loggingInterceptor: Interceptor, authInterceptor: Interceptor) =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

private fun provideRetrofit(client: OkHttpClient, gson: Gson) =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
