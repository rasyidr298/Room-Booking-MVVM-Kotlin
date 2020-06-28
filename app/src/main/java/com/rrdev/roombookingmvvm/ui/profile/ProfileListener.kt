package com.rrdev.roombookingmvvm.ui.profile

interface ProfileListener {
    fun onStarted()
    fun onSucces(message: String)
    fun onFailure(message: String)
}