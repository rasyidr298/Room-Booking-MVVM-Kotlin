package com.rrdev.mvvmkotlin.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.rrdev.mvvmkotlin.data.repositories.UserRepository
import com.rrdev.mvvmkotlin.util.Coroutines

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

        // memasang Coroutine di view model
        Coroutines.main{
            val response = UserRepository().userLogin(email!!,password!!)
            if (response.isSuccessful){
                authListener?.onSucces(response.body()?.user!!)
            }else{
                authListener?.onFailure("error code ${response.code()}")
            }
        }

    }
}