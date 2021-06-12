package com.example.spacexfanapplication.ui.home.spacexrockets

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceXFanApplication.ui.home.fragments.adapter.RocketsAdapter
import com.example.spacexfanapplication.R
import com.example.spacexfanapplication.base.BaseFragment
import com.example.spacexfanapplication.databinding.FragmentSpaceXRocketsBinding
import com.example.spacexfanapplication.ui.home.model.ErrorViewModel
import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse
import com.example.spacexfanapplication.ui.home.spaceXRockets.SpaceXRocketViewModel

class SpaceXRocketsFragment : BaseFragment<FragmentSpaceXRocketsBinding, SpaceXRocketViewModel>() {

    private var rocketsAdapter: RocketsAdapter? = null
    override fun getContentView(): Int = R.layout.fragment_space_x_rockets
    override fun setViewModelClass(): Class<SpaceXRocketViewModel> {
        return SpaceXRocketViewModel::class.java
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setUpRecyclerView()
        addObservers()
        callGetListApi()
    }

    private fun callGetListApi() {
        if (isNetworkAvailable()) {
            viewModel?.callSpaceXRocketsApi()
        } else {
            viewModel?.showErrorView(
                ErrorViewModel(
                    "No network available!",
                    getString(R.string.no_internet_connection)
                )
            )
        }
    }

    private fun addObservers() {
        viewModel?.getScreenLoading()?.observe(this, { show ->
            fragmentViewDataBinding?.progressSpaceXRockets?.visibility =
                if (show) View.VISIBLE else View.GONE
        })
        viewModel?.getErrorView()?.observe(this, { errorModel ->
            showErrorMessage(errorModel.heading, errorModel.description)
        })
        viewModel?.getSpaceXRocketsList()?.observe(this, { rocketsList ->
            rocketsList?.let { rocketsAdapter?.addItems(it) }
        })
    }

    private fun setUpRecyclerView() {
        rocketsAdapter = RocketsAdapter()
        fragmentViewDataBinding?.rvSpaceXRockets?.apply {
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
              /*  val intent= Intent(context, LaunchDetailsActivity::class.java)
                intent.putExtra(BUNDLE_ROCKET_DETAILS,data)
                launchDetailsRequest.launch(intent)*/
            }

        })
    }

/*
    val launchDetailsRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel?.setIsFav()
            }
        }
*/

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
        fragmentViewDataBinding?.errorViewSpaceXRockets?.let {
            it.showMessages(heading, desc)
            it.showImage(R.drawable.error)
            it.showButton()
            it.setButtonClick { _ ->
                if (isNetworkAvailable()) {
                    viewModel?.callSpaceXRocketsApi()
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