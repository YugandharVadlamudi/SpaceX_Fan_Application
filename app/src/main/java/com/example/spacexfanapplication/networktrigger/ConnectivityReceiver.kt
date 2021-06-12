package com.example.spacexfanapplication.networktrigger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.spacexfanapplication.networktrigger.model.ConnectivityModel


class ConnectivityReceiver(private val connectivityListener: ConnectivityReceiverListener?) :
    BroadcastReceiver() {
    companion object {
        const val TYPE_MOBILE = "MOBILE"
        const val TYPE_WIFI = "WIFI"
        const val TYPE_ETHERNET = "ETHERNET"
        var previousConnectivityModel: ConnectivityModel? = null

        fun isNetworkAvailable(context: Context): ConnectivityModel {
            val connectivityModel = ConnectivityModel()
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityModel.isConnected =
                connectivityManager.activeNetworkInfo?.isConnected ?: false
            Log.e("NETWORK_CHANGE", "isNetworkAvailable :: ${connectivityModel.isConnected}")
            (connectivityManager).run {
                getNetworkCapabilities(activeNetwork)?.let {
                    if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        connectivityModel.connectedType = TYPE_WIFI
                        connectivityModel.isConnected = true
                    } else if (it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        connectivityModel.connectedType = TYPE_ETHERNET
                        connectivityModel.isConnected = true
                    } else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        connectivityModel.connectedType = TYPE_MOBILE
                        connectivityModel.isConnected = true
                    } else {
                        connectivityModel.connectedType = TYPE_MOBILE
                        connectivityModel.isConnected = true
                    }
                }
            }

            return connectivityModel
        }
    }

    override fun onReceive(context: Context, arg1: Intent?) {
        Log.v("NETWORK_CHANGE", " :Called")
        val connectivityModel = isNetworkAvailable(context)
        Log.v("NETWORK_CHANGE", " CONNECTED : ${connectivityModel.isConnected}")
        Log.v("NETWORK_CHANGE", " CONNECTED TYPE : ${connectivityModel.connectedType}")
        if (previousConnectivityModel?.isConnected != connectivityModel.isConnected
//            || previousConnectivityModel?.connectedType != connectivityModel.connectedType
        ) {
            connectivityListener?.onNetworkConnectionChanged(connectivityModel)
            previousConnectivityModel = connectivityModel
        }
    }


    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(connectivityModel: ConnectivityModel)
    }

}