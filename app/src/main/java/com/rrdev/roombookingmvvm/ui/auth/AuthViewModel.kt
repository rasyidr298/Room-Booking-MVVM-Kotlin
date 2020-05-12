package com.rrdev.roombookingmvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.util.ApiException
import com.rrdev.roombookingmvvm.util.Coroutines

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var nim: String? = null
    var namaUser: String? = null
    var nohp: String? = null
    var password: String?= null
   // var passwordConfirm: String?= null
   val token = repository.getToken()
    //var token = sharedPrefToken?.deviceToken

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonnnClick(view: View){
        authListener?.onStarted()
        if (nim.isNullOrEmpty() || (password.isNullOrEmpty())){
            authListener?.onFailure("invalid username and password")
            return
        }

        // memasang Coroutine di view model
        Coroutines.main{
            try {
                val authResponse = repository.userLogin(nim!!,password!!)
                authResponse.user?.let {
                    authListener?.onSucces(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
        }

    }

    fun onLogin(view: View){
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUp(view: View){
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUpButtonnnClick(view: View){
        authListener?.onStarted()

        if (nim.isNullOrEmpty()){
            authListener?.onFailure("nim harus diisi")
            return
        }

        if (namaUser.isNullOrEmpty()){
            authListener?.onFailure("nama harus diisi")
            return
        }

        if (nohp.isNullOrEmpty()){
            authListener?.onFailure("nohp harus diisi")
            return
        }

        if (password.isNullOrEmpty()){
            authListener?.onFailure("password harus diisi")
            return
        }

//        if (password != passwordConfirm){
//            authListener?.onFailure("password tidak cocok")
//            return
//        }

//        if (token.isNullOrEmpty()){
//            authListener?.onFailure("token harus diisi")
//            return
//        }

        // memasang Coroutine di view model
        Coroutines.main{
            try {
                val authResponse = repository.userSignUp(nim!!,namaUser!!,password!!,nohp!!,token!!)
                authResponse.user?.let {
                    authListener?.onSucces(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
        }

    }
}