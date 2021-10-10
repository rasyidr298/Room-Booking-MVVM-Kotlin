package com.rrdev.roombookingmvvm.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.db.entities.Profile
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.SafeApiRequest
import com.rrdev.roombookingmvvm.data.network.responses.AuthResponse
import com.rrdev.roombookingmvvm.data.network.responses.ProfileResponse
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepository (
    private val api: MyApi,
    private val db: AppDatabase
    ): SafeApiRequest() {

    private val profile = MutableLiveData<Profile>()

    init {
        profile.observeForever {
            saveProfile(it)
        }
    }

    suspend fun getProfile(): LiveData<Profile> {
        return withContext(Dispatchers.IO) {
            fetchProfile()
            db.getProfileDao().getProfile()
        }
    }

    private suspend fun fetchProfile() {
        try {
            if (isFetchNeeded()) {
                val response = apiRequest { api.getProfile(prefManager.spNim) }
                profile.postValue(response.profile)
            }
        } catch (e: NoInternetException) {
            Log.e("Connectivty", "No INTERNET CONNECTION")
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    fun saveProfile(profile: Profile) {
        Coroutines.io {
            db.getProfileDao().saveProfile(profile)
        }
    }

    suspend fun updateProfile(
        nimParams: String, nim: String,namaUser: String, nohp: String, password: String
    ): ProfileResponse {
        return apiRequest { api.updateProfile(nimParams, nim,namaUser,nohp,password) }
    }
}