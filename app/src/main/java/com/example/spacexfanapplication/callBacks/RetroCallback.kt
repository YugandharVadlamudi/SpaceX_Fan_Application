package com.example.spacexfanapplication.callBacks

import com.example.spacexfanapplication.MyApplication
import com.example.spacexfanapplication.R
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException



class RetroCallback<T>(private val mCallback: MyCallback<T>) :
    Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (call.isCanceled) {
            mCallback.onApiCanceled()
            return
        }
        if (response.isSuccessful && response.code() == 200) {
            try {
                val responseBody: T? = response.body()
                mCallback.onResponse(call, responseBody)
            } catch (e: Exception) {
                mCallback.onResponse(call, null)
            }
        } else {
            val errorBody = response.errorBody()?.string()?:""
            val errorJsonObject = JSONObject(errorBody)
            val errorMessage = if(errorJsonObject.has("errorMessage"))errorJsonObject.getString("errorMessage") else "Unknown"
            mCallback.onError(R.drawable.error, MyApplication.getApplicationContext().getString(R.string.error), errorMessage,response.code())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (call.isCanceled) {
            mCallback.onApiCanceled()
            return
        }
        when (t) {
            is HttpException -> mCallback.onError(R.drawable.error,MyApplication.getApplicationContext().getString(R.string.error),"HttpException")
            is UnknownHostException->mCallback.onError(R.drawable.error,MyApplication.getApplicationContext().getString(R.string.error),"UnknownHostException")
            is SocketTimeoutException -> mCallback.onTimeout(R.drawable.error,MyApplication.getApplicationContext().getString(R.string.error),"SocketTimeoutException")
            is IOException -> mCallback.onError(R.drawable.error,MyApplication.getApplicationContext().getString(R.string.error),"IOException")
            else -> mCallback.onError(R.drawable.error,MyApplication.getApplicationContext().getString(R.string.error), t.message)
        }

    }

    interface MyCallback<V> {
        fun onResponse(call: Call<V>, response: V?)
        fun onTimeout(icon:Int?,heading:String?,desc:String?)
        fun onError(icon:Int?,heading:String?,desc:String?,statusCode:Int?=-1)
        fun onApiCanceled()
    }
}
