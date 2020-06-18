package com.rrdev.roombookingmvvm.data.network.responses
import com.google.gson.annotations.SerializedName
import com.rrdev.roombookingmvvm.data.db.entities.User

data class AuthResponse(
    val status: String,
    val message: String,
    @SerializedName("data")
    val user: User?
)