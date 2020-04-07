package com.rrdev.mvvmkotlin.data.repositories
import com.rrdev.mvvmkotlin.data.network.MyApi
import com.rrdev.mvvmkotlin.data.network.SafeApiRequest
import com.rrdev.mvvmtrial.data.network.responses.AuthResponse
import retrofit2.Response


//Response dgn coroutinee
class UserRepository :SafeApiRequest() {
    suspend fun userLogin(email: String, password: String ): AuthResponse {
        return apiRequest { MyApi().userLogin(email,password) }
    }
}