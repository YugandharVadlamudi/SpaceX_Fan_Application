package com.example.spacexfanapplication.ui.home.favoriteRockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.spacexfanapplication.MyApplication
import com.example.spacexfanapplication.base.BaseViewModel
import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse

class FavoriteRocketViewModel : BaseViewModel() {
    private val showScreenLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val favList: MutableLiveData<ArrayList<LaunchDetailsResponse?>> = MutableLiveData()
    fun getScreenLoading(): LiveData<Boolean> {
        return showScreenLoading
    }

     fun showScreenLoading(show: Boolean) {
        showScreenLoading.value = show
    }

    fun getFavList():LiveData<ArrayList<LaunchDetailsResponse?>> {
        return favList
    }

    private fun setFavList(favList:ArrayList<LaunchDetailsResponse?>){
        this.favList.value=favList
    }
    fun setIsFav(){
        val list= MyApplication.mFavoriteList.value
        if (list!= null){
            for (item in list) {
                item?.isFav = true
            }
            setFavList(list)
        }
        showScreenLoading(false)
    }
}