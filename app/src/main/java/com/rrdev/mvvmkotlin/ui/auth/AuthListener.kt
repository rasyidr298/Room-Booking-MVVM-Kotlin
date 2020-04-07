package com.rrdev.mvvmkotlin.ui.auth

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSucces(loginResponse: LiveData<String>)
    fun onFailure(message: String)
}