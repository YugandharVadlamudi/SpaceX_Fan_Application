package com.example.spacexfanapplication.network.retrofit

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


open class BaseOkHttp {
    companion object{
        const val NETWORK_CONNECTION_TIMEOUT = 60L
    }


    /**
     * provideOKHttpClient - To Provide OkHttpClient object with header
     * @since 1.0
     * @returns OkHttpClient
     */
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(NETWORK_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }


}