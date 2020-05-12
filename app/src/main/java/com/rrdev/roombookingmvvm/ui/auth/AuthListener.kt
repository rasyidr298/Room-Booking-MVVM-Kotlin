package com.rrdev.roombookingmvvm.ui.auth
import com.rrdev.roombookingmvvm.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSucces(user: User)
    fun onFailure(message: String)
}