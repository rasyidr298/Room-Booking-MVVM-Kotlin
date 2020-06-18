package com.rrdev.roombookingmvvm.data.repositories
import com.rrdev.roombookingmvvm.data.SharedPreferences.SharedPrefToken
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.db.entities.User
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.SafeApiRequest
import com.rrdev.roombookingmvvm.data.network.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val sharedPrefToken: SharedPrefToken
) :SafeApiRequest() {

    suspend fun userLogin(nim: String, password: String ): AuthResponse {
        return apiRequest { api.userLogin(nim,password) }
    }

    suspend fun userSignUp(
        nim: String, namaUser: String, nohp: String,
        password: String, token: String
    ): AuthResponse{
        return apiRequest { api.userSignUp(nim,namaUser,nohp,password,token) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().saveUser(user)

    fun getUser() = db.getUserDao().getUser()

    fun getToken() = sharedPrefToken.deviceToken

}