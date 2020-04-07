package com.rrdev.mvvmkotlin.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.rrdev.mvvmkotlin.data.repositories.UserRepository

class AuthViewModel : ViewModel() {
    var email: String? = null
    var password: String?= null

    var authListener: AuthListener? = null

    fun onLoginButtonnnClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty() || (password.isNullOrEmpty())){
            authListener?.onFailure("invalid username and password")
            return
        }
        val loginResponse = UserRepository().userLogin(email!!,password!!)
        authListener?.onSucces(loginResponse)
    }
}