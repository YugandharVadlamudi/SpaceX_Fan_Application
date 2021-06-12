package com.example.spaceXFanApplication.ui.sigiin

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.spacexfanapplication.MyApplication
import com.example.spacexfanapplication.R
import com.example.spacexfanapplication.base.BaseActivity
import com.example.spacexfanapplication.databinding.ActivitySignInBinding
import com.example.spacexfanapplication.ui.home.HomeActivity
import com.example.spacexfanapplication.ui.sigiin.SignInViewModel
import com.example.spacexfanapplication.util.hideKeyboard
import com.example.spacexfanapplication.util.isValidEmail
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>(),
    View.OnClickListener {


    override fun getContentView(): Int = R.layout.activity_sign_in
    override fun setViewModelClass(): Class<SignInViewModel> = SignInViewModel::class.java
    override fun initViews(savedInstanceState: Bundle?) {
        setClickListeners()
    }

    private fun setClickListeners() {
        viewDataBinding?.btnLogin?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            viewDataBinding?.btnLogin -> {
                if (isNetworkAvailable()){
                    validateFields()
                }else{
                    noInternetError()
                }
            }
        }
    }

    private fun validateFields() {
        val email = viewDataBinding?.etEmail?.text?.trim()?.toString()
        val password = viewDataBinding?.etPassword?.text?.trim()?.toString()
        if (email.isNullOrEmpty()) {
            showErrorMessage(getString(R.string.empty_email_error))
        } else if (!isValidEmail(email)) {
            showErrorMessage(getString(R.string.invalid_email_error))
        } else if (password.isNullOrEmpty()) {
            showErrorMessage(getString(R.string.empty_password_error))
        } else if (password.length < 8) {
            showErrorMessage(getString(R.string.invalid_password_error))
        } else {
            viewDataBinding?.progressSignIn?.visibility=View.VISIBLE
            hideKeyboard(this)
            signInToFireBase(email, password)
        }
    }

    private fun signInToFireBase(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                MyApplication.mCurrentUser.value = mAuth.currentUser
                viewModel?.getFavoriteList {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    viewDataBinding?.progressSignIn?.visibility=View.GONE
                }
            } else {
                showErrorMessage(task.exception?.message)
                viewDataBinding?.progressSignIn?.visibility=View.GONE
            }
        }
    }
}