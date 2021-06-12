package com.example.spacexfanapplication.base

import androidx.lifecycle.ViewModel
import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse
import com.example.spacexfanapplication.MyApplication
import com.example.spacexfanapplication.util.SingleLiveEvent
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call


open class BaseViewModel :
    ViewModel() {

    private val callList: ArrayList<Call<*>> = ArrayList()
    val showSuccessMessage: SingleLiveEvent<String?> = SingleLiveEvent()
    val showErrorMessage: SingleLiveEvent<String?> = SingleLiveEvent()
    private val db = Firebase.firestore
    fun clearCalls() {
        for (callItem in callList) {
            callItem.cancel()
        }
        callList.clear()
    }

    fun addCall(call: Call<*>) {
        callList.add(call)
    }

    fun getFavoriteList(callback: (success: Boolean) -> Unit) {
        db.collection(MyApplication.mCurrentUser.value?.uid ?: "").get()
            .addOnSuccessListener { querySnapShot ->
                val data =
                    querySnapShot.toObjects(LaunchDetailsResponse::class.java) as? ArrayList<LaunchDetailsResponse?>
                MyApplication.mFavoriteList.value = data
                callback(true)
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false)
            }
    }

    fun addFavoriteItem(
        launchDetailsResponse: LaunchDetailsResponse?,
        callback: (success: Boolean) -> Unit
    ) {
        launchDetailsResponse?.let {
            db.collection(MyApplication.mCurrentUser.value?.uid ?: "")
                .document(launchDetailsResponse.id ?: "").set(
                    it
                ).addOnSuccessListener {
                    getFavoriteList { isSuccess ->
                        callback(isSuccess)
                    }
                }.addOnFailureListener {exception->
                    exception.printStackTrace()
                    callback(false)
                }
        }
    }

    fun deleteFavoriteItem(
        launchDetailsResponse: LaunchDetailsResponse?,
        callback: (success: Boolean) -> Unit
    ) {
        launchDetailsResponse?.let {
            db.collection(MyApplication.mCurrentUser.value?.uid ?: "")
                .document(launchDetailsResponse.id ?: "").delete().addOnSuccessListener {
                    getFavoriteList { isSuccess ->
                        callback(isSuccess)
                    }
                }.addOnFailureListener {exception->
                    exception.printStackTrace()
                    callback(false)
                }
        }
    }
}