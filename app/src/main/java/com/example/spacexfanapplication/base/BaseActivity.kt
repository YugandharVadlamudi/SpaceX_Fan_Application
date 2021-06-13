package com.example.spacexfanapplication.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.spacexfanapplication.R
import com.example.spacexfanapplication.ui.sigiin.SignInActivity
import com.example.spacexfanapplication.util.hideKeyboard
import com.example.spacexfanapplication.util.showSnackBar


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> :
    AppCompatActivity() {

    var viewDataBinding: T? = null
    var viewModel: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        initViews(savedInstanceState)
        addObservers()
        hideKeyboard(this)
    }

    private fun addObservers() {
        viewModel?.showSuccessMessage?.observe(this, {
            it?.let {
                showSnackBar(it, this@BaseActivity, true)
            }
        })
        viewModel?.showErrorMessage?.observe(this, { message ->
            message?.let {
                showSnackBar(it, this, false)
            }
        })
    }


    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getContentView())
        viewDataBinding?.lifecycleOwner = this
        this.viewModel = ViewModelProvider(this)[setViewModelClass()]
//        viewDataBinding?.setVariable(getBindingVariable(), viewModel)
        viewDataBinding?.executePendingBindings()
    }

    /**
     * Return layout id for each screen
     */
    abstract fun getContentView(): Int

    /**
     * Assign view model for appropriate screen
     */

    abstract fun setViewModelClass(): Class<V>


    abstract fun initViews(savedInstanceState: Bundle?)


    fun navigateToLoginScreen() {
        val intent = Intent(this@BaseActivity, SignInActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun showErrorMessage(message:String?){
       viewModel?.showErrorMessage?.value=message
    }

    fun showSuccessMessage(message:String?){
        viewModel?.showSuccessMessage?.value=message
    }

    fun isNetworkAvailable() =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }

    fun noInternetError() {
        viewModel?.showErrorMessage?.value=getString(R.string.no_internet_connection)
    }

    override fun onDestroy() {
        viewModel?.clearCalls()
        super.onDestroy()
    }

}