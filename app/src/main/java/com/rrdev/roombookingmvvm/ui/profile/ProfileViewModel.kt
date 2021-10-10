package com.rrdev.roombookingmvvm.ui.profile

import android.view.View
import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.data.repositories.ProfileRepository
import com.rrdev.roombookingmvvm.util.ApiException
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.NoInternetException
import com.rrdev.roombookingmvvm.util.lazyDeferred

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    var nim = prefManager.spNim
    var nama: String? = null
    var nohp: String? = null
    var password: String? = null

    var profileListener: ProfileListener? = null

    fun onUpdateButtonnnClick(view: View){
        profileListener?.onStarted()
        //validasi field kosong
        if (nama.isNullOrEmpty() || (nohp.isNullOrEmpty() || (password.isNullOrEmpty() ))) {
            profileListener?.onFailure("harus terisi !!")
            return
        }

        Coroutines.main {
            try {
                val profileResponse = repository.updateProfile(nim!!,nim!!,nama!!,nohp!!,password!!)
                profileResponse.profile.let {
                    profileListener?.onSucces("data nama "+it.namaUser+" sukses diubah")
                    prefManager.spNama = it.namaUser
                    repository.saveProfile(it)
                    return@main
                }
                //profileListener?.onFailure(profileResponse.status.toString())
            }catch (e: ApiException){
                profileListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                profileListener?.onFailure(e.message!!)
            }
        }
    }

    val profile by lazyDeferred{
        repository.getProfile()
    }
}
