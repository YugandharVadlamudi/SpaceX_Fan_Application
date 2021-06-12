package com.example.spacexfanapplication.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import com.example.spaceXFanApplication.ui.sigiin.SignInActivity
import com.example.spacexfanapplication.MyApplication
import com.example.spacexfanapplication.R
import com.example.spacexfanapplication.base.BaseActivity
import com.example.spacexfanapplication.databinding.ActivitySplashScreenBinding
import com.example.spacexfanapplication.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>() {

    override fun getContentView(): Int = R.layout.activity_splash_screen
    override fun setViewModelClass(): Class<SplashScreenViewModel> {
        return SplashScreenViewModel::class.java
    }

    override fun initViews(savedInstanceState: Bundle?) {
        val mCurrentUser = FirebaseAuth.getInstance().currentUser
        if (mCurrentUser != null) {
            MyApplication.mCurrentUser.value = mCurrentUser
            viewModel?.getFavoriteList {
                navigateTo(HomeActivity::class.java)
            }
        } else {
            navigateTo(SignInActivity::class.java)
        }
    }

    private fun navigateTo(screenName: Class<*>) {
        startActivity(Intent(this@SplashScreenActivity, screenName))
        finish()
    }

}