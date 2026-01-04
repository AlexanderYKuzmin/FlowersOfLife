package com.kuzmin.flowersoflife.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kuzmin.flowersoflife.core.BuildConfig
import com.kuzmin.flowersoflife.core.api.ApiService
import com.kuzmin.flowersoflife.core.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    factory<AuthInterceptor> { AuthInterceptor(get()) }

    single<Gson> {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}