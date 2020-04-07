package com.rrdev.mvvmkotlin.data.repositories
import com.rrdev.mvvmkotlin.data.network.MyApi
import com.rrdev.mvvmtrial.data.network.responses.AuthResponse
import retrofit2.Response


//Response dgn coroutine
class UserRepository {
    suspend fun userLogin(email: String, password: String ): Response <AuthResponse> {
        return MyApi().userLogin(email,password)
    }
}