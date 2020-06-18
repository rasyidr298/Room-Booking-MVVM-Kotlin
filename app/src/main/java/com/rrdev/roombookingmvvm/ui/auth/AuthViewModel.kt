package com.rrdev.roombookingmvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.util.ApiException
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var nim: String? = null
    var namaUser: String? = null
    var nohp: String? = null
    var password: String? = null
    val token = repository.getToken()

    var authListener: AuthListener? = null

    fun onLoginButtonnnClick(view: View) {
        authListener?.onStarted()
        //validasi field kosong
        if (nim.isNullOrEmpty() || (password.isNullOrEmpty())) {
            authListener?.onFailure("invalid username and password")
            return
        }

        //login request
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(nim!!, password!!)
                authResponse.user?.let {
                    authListener?.onSucces(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }

    //request signup
    fun onSignUpButtonnnClick(view: View) {
        authListener?.onStarted()

        //validasi field
        if (nim.isNullOrEmpty() || namaUser.isNullOrEmpty() || nohp.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("harus terisi semuaa !!")
            return
        }

        // request register
        Coroutines.main {
            try {
                val authResponse =
                    repository.userSignUp(nim!!, namaUser!!, nohp!!, password!!, token!!)
                authResponse.user?.let {
                    authListener?.onSucces(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}