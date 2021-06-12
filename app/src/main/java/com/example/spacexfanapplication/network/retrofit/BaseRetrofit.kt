package com.example.spacexfanapplication.network.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


object BaseRetrofit : BaseOkHttp() {
    const val BASE_URL = "https://api.spacexdata.com/v4/"
    private var retrofit: Retrofit? = null

    /**
     * getMyRetrofit - To create retrofit object
     * @since 1.0
     * @returns Retrofit
     */
    fun getMyRetrofit(): Retrofit {
        if (retrofit == null) {
            createUsRetrofit()
        }
        return retrofit!!
    }

    /**
     * createUsRetrofit - To create retrofit  for us base url
     * @since 1.0
     */
    private fun createUsRetrofit() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOKHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


}