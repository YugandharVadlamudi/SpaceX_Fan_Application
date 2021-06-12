package com.example.spacexfanapplication

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.*
import com.example.spacexfanapplication.networktrigger.ConnectivityReceiver
import com.example.spacexfanapplication.networktrigger.model.ConnectivityModel
import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse
import com.google.firebase.auth.FirebaseUser

class MyApplication : Application(), LifecycleObserver {
    init {
        myApplication = this
    }

    companion object {
        private lateinit var myApplication: Application
        val networkChangeLiveData: MutableLiveData<ConnectivityModel> = MutableLiveData()
        val mCurrentUser: MutableLiveData<FirebaseUser> = MutableLiveData()
        val mFavoriteList: MutableLiveData<ArrayList<LaunchDetailsResponse?>?> = MutableLiveData()
        fun getApplicationContext(): Context {
            return myApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    private fun registerNetworkReceiver() {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        registerReceiver(connectivityReceiver, filter)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateApplication() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        registerNetworkReceiver()
    }

    private val connectivityReceiver =
        ConnectivityReceiver(object : ConnectivityReceiver.ConnectivityReceiverListener {
            override fun onNetworkConnectionChanged(
                connectivityModel: ConnectivityModel
            ) {
                Log.e(
                    "NETWORK_CHANGE_INFO",
                    ": CONNECTED: ${connectivityModel.isConnected},  CONNECTED TYPE: ${connectivityModel.connectedType}"
                )
                networkChangeLiveData.value = connectivityModel
            }
        })

    /**
     * Shows background
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        unRegisterNetworkReceiver()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyApplication() {
        ConnectivityReceiver.previousConnectivityModel = null
        unRegisterNetworkReceiver()
    }

    private fun unRegisterNetworkReceiver() {
        unregisterReceiver(connectivityReceiver)
    }


}