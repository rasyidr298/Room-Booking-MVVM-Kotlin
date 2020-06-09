package com.rrdev.roombookingmvvm.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rrdev.mvvmtrial.data.db.AppDatabase
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.data.db.entities.Booking
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.SafeApiRequest
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookingRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    private val booking = MutableLiveData<List<Booking>>()

    init {
        booking.observeForever{
            saveBooking(it)
        }
    }

    suspend fun getBooking(): LiveData<List<Booking>>{
        return withContext(Dispatchers.IO){
            fetchBooking()
            db.getBookingDao().getBooking()
        }
    }

    private suspend fun fetchBooking(){
        try {
            if (isFetchNeeded()){
                val response = apiRequest { api.getBooking(prefManager.spNim) }
                booking.postValue(response.booking)
            }
        }catch (e: NoInternetException){
            Log.e("Connectivty","No INTERNET CONNECTION")
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    private fun saveBooking(booking: List<Booking>){
        Coroutines.io {
            db.getBookingDao().saveBooking(booking)
        }
    }

//    suspend fun getDetailRoomById(
//        idRoom: Int
//    ):LiveData<DetailRooms>{
//        return withContext(Dispatchers.IO){
//            fetchBooking()
//            db.getRoomDao().getDetailRoom(idRoom)
//        }
//    }
}