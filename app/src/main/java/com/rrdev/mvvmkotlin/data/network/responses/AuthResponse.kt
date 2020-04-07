package com.rrdev.mvvmtrial.data.network.responses

import com.rrdev.mvvmtrial.data.db.entities.User

//mengambil respon Api
data class AuthResponse(
    val isSuccesFul: Boolean?,
    val message: String?,
    val user: User?
)