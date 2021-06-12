package com.example.spacexfanapplication.network.retrofit

import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse
import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("launches")
    fun getLaunchList(): Call<ArrayList<LaunchDetailsResponse?>>

    @GET("launches/upcoming")
    fun getUpcomingLaunchList(): Call<ArrayList<LaunchDetailsResponse?>>
}