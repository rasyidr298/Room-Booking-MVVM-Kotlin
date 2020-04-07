package com.rrdev.mvvmkotlin.ui.auth

import com.rrdev.mvvmtrial.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSucces(user: User)
    fun onFailure(message: String)
}