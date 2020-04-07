package com.rrdev.mvvmkotlin.ui.auth

interface AuthListener {
    fun onStarted()
    fun onSucces()
    fun onFailure(message: String)
}