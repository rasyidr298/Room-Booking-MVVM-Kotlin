package com.rrdev.roombookingmvvm.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.db.entities.DetailRooms
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.SafeApiRequest
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    private val room = MutableLiveData<List<Rooms>>()

    init {
        room.observeForever {
            saveRoom(it)
        }
    }

    suspend fun getRoom(): LiveData<List<Rooms>> {
        return withContext(Dispatchers.IO) {
            fetchRoom()
            db.getRoomDao().getRoom()
        }
    }

    private suspend fun fetchRoom() {
        try {
            if (isFetchNeeded()) {
                val response = apiRequest { api.getRoom() }
                room.postValue(response.rooms)
            }
        } catch (e: NoInternetException) {
            Log.e("Connectivty", "No INTERNET CONNECTION")
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    private fun saveRoom(rooms: List<Rooms>) {
        Coroutines.io {
            db.getRoomDao().saveAllRoom(rooms)
        }
    }

    suspend fun getDetailRoomByName(): LiveData<DetailRooms> {
        return withContext(Dispatchers.IO) {
            fetchRoom()
            db.getRoomDao().getDetailRoom(prefManager.spNamaRoom.toString())
        }
    }

    fun getUser() = db.getUserDao().getUser()
}