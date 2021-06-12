package com.example.spacexfanapplication.ui.home.spaceXRockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.spacexfanapplication.base.BaseViewModel
import com.example.spacexfanapplication.callBacks.RetroCallback
import com.example.spacexfanapplication.network.ApiHelper
import com.example.spacexfanapplication.ui.home.model.ErrorViewModel
import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse
import com.example.spacexfanapplication.util.isFavItem
import retrofit2.Call

class SpaceXRocketViewModel : BaseViewModel() {
    private val showScreenLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val errorView: MutableLiveData<ErrorViewModel> = MutableLiveData()
    private val spaceXRocketsList: MutableLiveData<ArrayList<LaunchDetailsResponse?>?> = MutableLiveData()
    private val responseList: MutableLiveData<ArrayList<LaunchDetailsResponse?>?> = MutableLiveData()

    fun getScreenLoading(): LiveData<Boolean> {
        return showScreenLoading
    }

     fun showScreenLoading(show: Boolean) {
        showScreenLoading.value = show
    }

     fun showErrorView(errorView: ErrorViewModel) {
        this.errorView.value = errorView
    }

    fun getErrorView(): LiveData<ErrorViewModel> {
        return errorView
    }

    fun getSpaceXRocketsList(): LiveData<ArrayList<LaunchDetailsResponse?>?> {
        return spaceXRocketsList
    }

    private fun setSpaceXRocketsList(spaceXRocketsList: ArrayList<LaunchDetailsResponse?>?) {
        this.spaceXRocketsList.value = spaceXRocketsList
    }

    fun setIsFav(){
        if (responseList.value != null){
            for (item in responseList.value!!) {
                isFavItem(item?.id ?: "") { isFav ->
                    item?.isFav = isFav
                }
            }
            setSpaceXRocketsList(responseList.value)
        }
        showScreenLoading(false)
    }

    fun callSpaceXRocketsApi() {
        showScreenLoading(true)
        val call = ApiHelper().getApi().getLaunchList()
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