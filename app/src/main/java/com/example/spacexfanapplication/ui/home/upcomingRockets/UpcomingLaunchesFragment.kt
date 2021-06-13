package com.example.spaceXFanApplication.ui.home.fragments.upcomingRockets
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfanapplication.R
import com.example.spacexfanapplication.base.BaseFragment
import com.example.spacexfanapplication.databinding.FragmentUpcomingRocketsBinding
import com.example.spacexfanapplication.ui.home.adapter.RocketsAdapter
import com.example.spacexfanapplication.ui.home.model.ErrorViewModel
import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse
import com.example.spacexfanapplication.util.BUNDLE_ROCKET_DETAILS

class UpcomingLaunchesFragment:
    BaseFragment<FragmentUpcomingRocketsBinding, UpcomingRocketViewModel>(){
    private var rocketsAdapter: RocketsAdapter? = null
    override fun getContentView(): Int = R.layout.fragment_upcoming_rockets
    override fun setViewModelClass(): Class<UpcomingRocketViewModel> {
    return UpcomingRocketViewModel::class.java
    }
    override fun initViews(savedInstanceState: Bundle?) {
        setUpRecyclerView()
        addObservers()
        callGetListApi()
    }
    private fun callGetListApi() {
        if (isNetworkAvailable()){
            viewModel?.callUpcomingRocketsApi()
        }else{
            viewModel?.showErrorView(ErrorViewModel("No network available!",getString(R.string.no_internet_connection)))
        }
    }

    private fun addObservers() {
        viewModel?.getScreenLoading()?.observe(this, { show ->
            fragmentViewDataBinding?.progressUpcomingRockets?.visibility =
                if (show) View.VISIBLE else View.GONE
        })
        viewModel?.getErrorView()?.observe(this, { errorModel ->
            showErrorMessage(errorModel.heading, errorModel.description)
        })
        viewModel?.getUpcomingRocketsList()?.observe(this, { rocketsList ->
            rocketsList?.let { rocketsAdapter?.addItems(it) }
        })
    }

    private fun setUpRecyclerView() {
        rocketsAdapter = RocketsAdapter()
        fragmentViewDataBinding?.rvUpcomingRockets?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rocketsAdapter
            itemAnimator?.changeDuration = 0
        }
        rocketsAdapter?.setClickListener(object : RocketsAdapter.ClickListener {
            override fun addFav(data: LaunchDetailsResponse) {
                addFavItem(data)
            }

            override fun removeFav(data: LaunchDetailsResponse) {
                removeFavItem(data)
            }

            override fun onItemClick(data: LaunchDetailsResponse) {
               /* val intent= Intent(context, LaunchDetailsActivity::class.java)
                intent.putExtra(BUNDLE_ROCKET_DETAILS,data)
                launchDetailsRequest.launch(intent)*/
            }

        })
    }
    val launchDetailsRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel?.setIsFav()
            }
        }

    private fun removeFavItem(data: LaunchDetailsResponse) {
        if (isNetworkAvailable()) {
            viewModel?.showScreenLoading(true)
            viewModel?.deleteFavoriteItem(data) { success ->
                if (success) {
                    viewModel?.setIsFav()
                } else {
                    viewModel?.showScreenLoading(false)
                }
            }
        } else {
            noNetworkError()
        }
    }

    fun addFavItem(data: LaunchDetailsResponse) {
        if (isNetworkAvailable()) {
            viewModel?.showScreenLoading(true)
            viewModel?.addFavoriteItem(data) { success ->
                if (success) {
                    viewModel?.setIsFav()
                } else {
                    viewModel?.showScreenLoading(false)
                }
            }
        } else {
            noNetworkError()
        }
    }

    private fun showErrorMessage(
        heading: String?,
        desc: String?
    ) {
        fragmentViewDataBinding?.errorViewUpcomingRockets?.let {
            it.showMessages(heading, desc)
            it.showImage(R.drawable.error)
            it.showButton()
            it.setButtonClick { _ ->
                if (isNetworkAvailable()) {
                    viewModel?.callUpcomingRocketsApi()
                    it.hide()
                } else {
                    noNetworkError()
                }
            }
            it.show()
            it.visibility = View.VISIBLE
        }
    }
}