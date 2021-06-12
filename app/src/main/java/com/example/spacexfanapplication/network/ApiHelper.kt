package com.example.spacexfanapplication.network

import com.example.spacexfanapplication.network.retrofit.API
import com.example.spacexfanapplication.network.retrofit.BaseRetrofit

class ApiHelper {
    /**
     * getApi - To return interface of api
     * @param assets: assets
     * @since 1.0
     */
    fun getApi(): API {
        return BaseRetrofit.getMyRetrofit().create(API::class.java)
    }
}