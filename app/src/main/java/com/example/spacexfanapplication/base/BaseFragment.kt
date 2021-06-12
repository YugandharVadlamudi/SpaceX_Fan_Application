package com.example.spacexfanapplication.base


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> :
    Fragment(){

    var fragmentViewDataBinding: T? = null
    var viewModel: V? = null
    private val mainHandler:Handler =  Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentViewDataBinding =
            DataBindingUtil.inflate(inflater, getContentView(), container, false)
        fragmentViewDataBinding?.lifecycleOwner = this
        this.viewModel = ViewModelProvider(this)[setViewModelClass()]
        return fragmentViewDataBinding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainHandler.postDelayed(initCalls, 0L)
    }

    private val initCalls = Runnable {
        initViews(null)
    }
    private fun addObservers() {
        viewModel?.showSuccessMessage?.observe(this, {
            (activity as? BaseActivity<*, *>)?.viewModel?.showSuccessMessage?.value=it
        })
        viewModel?.showErrorMessage?.observe(this, { message ->
            (activity as? BaseActivity<*, *>)?.viewModel?.showErrorMessage?.value=message
        })
    }
    fun isNetworkAvailable() =(activity as? BaseActivity<*, *>)?.isNetworkAvailable()?:false

    fun noNetworkError()=(activity as? BaseActivity<*, *>)?.noInternetError()

    abstract fun getContentView(): Int

    abstract fun setViewModelClass(): Class<V>

    abstract fun initViews(savedInstanceState: Bundle?)

    fun showSuccessMessage(message:String?){
        (activity as? BaseActivity<*, *>)?.showSuccessMessage(message)
    }

    fun showErrorMessage(message:String?){
        (activity as? BaseActivity<*, *>)?.showErrorMessage(message)
    }

    override fun onDestroyView() {
        viewModel?.clearCalls()
        super.onDestroyView()
    }

}