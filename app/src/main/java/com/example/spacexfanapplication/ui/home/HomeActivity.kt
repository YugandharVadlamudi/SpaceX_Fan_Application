package com.example.spaceXFanApplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.spacexfanapplication.R
import com.example.spacexfanapplication.base.BaseActivity
import com.example.spacexfanapplication.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    private var currentLoadedFragment: Fragment? = null

    override fun getContentView(): Int = R.layout.activity_home
    override fun setViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
    override fun initViews(savedInstanceState: Bundle?) {
        setListeners()
//        loadFragment(SpaceXRocketsFragment())
        viewDataBinding?.txtToolbarSignOut?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            navigateToLoginScreen()
        }
    }

    private fun setListeners() {
        viewDataBinding?.tlHome?.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
/*
                when (tab?.position) {
                    0 -> {
                        loadFragment(SpaceXRocketsFragment())
                    }
                    1 -> {
                        loadFragment(FavoriteRocketFragment())
                    }
                    2 -> {
                        loadFragment(UpcomingLaunchesFragment())
                    }
                }
*/
            }
        })
    }

    fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        currentLoadedFragment = fragment
        val fragmentTransaction = ft.replace(R.id.flContainer, fragment)
        fragmentTransaction.commit()
    }
}