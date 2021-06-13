package com.example.spaceXFanApplication.ui.home.fragments.upcomingRockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.spacexfanapplication.base.BaseViewModel
import com.example.spacexfanapplication.callBacks.RetroCallback
import com.example.spacexfanapplication.network.ApiHelper
import com.example.spacexfanapplication.ui.home.model.ErrorViewModel
import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse
import com.example.spacexfanapplication.util.isFavItem
import retrofit2.Call

class UpcomingRocketViewModel: BaseViewModel() {
    private val showScreenLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val upcomingRocketsList: MutableLiveData<ArrayList<LaunchDetailsResponse?>?> = MutableLiveData()
    private val errorView: MutableLiveData<ErrorViewModel> = MutableLiveData()
    private val responseList: MutableLiveData<ArrayList<LaunchDetailsResponse?>?> = MutableLiveData()

    fun getScreenLoading(): LiveData<Boolean> {
        return showScreenLoading
    }

     fun showScreenLoading(show: Boolean) {
        showScreenLoading.value = show
    }

    fun getUpcomingRocketsList(): LiveData<ArrayList<LaunchDetailsResponse?>?> {
        return upcomingRocketsList
    }

    private fun setUpcomingRocketsList(upcomingRocketsList:ArrayList<LaunchDetailsResponse?>?){
        this.upcomingRocketsList.value=upcomingRocketsList
    }
     fun showErrorView(errorView: ErrorViewModel) {
        this.errorView.value = errorView
    }

    fun getErrorView(): LiveData<ErrorViewModel> {
        return errorView
    }

    fun setIsFav(){
        if (responseList.value != null){
            for (item in responseList.value!!) {
                isFavItem(item?.id ?: "") { isFav ->
                    item?.isFav = isFav
                }
            }
            setUpcomingRocketsList(responseList.value)
        }
        showScreenLoading(false)
    }
    fun callUpcomingRocketsApi() {
        showScreenLoading(true)
        val call = ApiHelper().getApi().getUpcomingLaunchList()
        addCall(call)
        call.enqueue(RetroCallback(object :
            RetroCallback.MyCallback<ArrayList<LaunchDetailsResponse?>?> {
            override fun onResponse(
                call: Call<ArrayList<LaunchDetailsResponse?>?>,
                response: ArrayList<LaunchDetailsResponse?>?
            ) {
                if (response != null) {
                    responseList.value=response
                    setIsFav()
                } else {
                    showScreenLoading(false)
                    showErrorView(ErrorViewModel("Error","No data available!"))
                }
            }

            override fun onTimeout(icon: Int?, heading: String?, desc: String?) {
                showScreenLoading(false)
                showErrorView(ErrorViewModel(heading,desc))
            }

            override fun onError(icon: Int?, heading: String?, desc: String?, statusCode: Int?) {
                showScreenLoading(false)
                showErrorView(ErrorViewModel(heading,desc))
            }

            override fun onApiCanceled() {
                showScreenLoading(false)
            }

        }))
    }
}