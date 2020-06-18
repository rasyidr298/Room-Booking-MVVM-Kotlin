package com.rrdev.roombookingmvvm.ui.bookingRoom

import android.view.View
import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.data.repositories.BookingRoomRepository
import com.rrdev.roombookingmvvm.util.ApiException
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.NoInternetException

class BookingRoomViewModel(
    private val repository: BookingRoomRepository
) : ViewModel() {
    var tanggal: String? = null
    var jamMulai: String? = null
    var jamSelesai: String? = null
    var keterangan: String?= null

    var bookingRoomListener: BookingRoomListener? = null

    fun onBookingButtonClick(view: View){
        bookingRoomListener?.onStarted()
        //validasi fieldkosong
        if ((tanggal.isNullOrEmpty() || jamMulai.isNullOrEmpty() || jamSelesai.isNullOrEmpty() || keterangan.isNullOrEmpty() )){
            bookingRoomListener?.onFailure("harus terisi semuaa")
            return
        }

        //push booking
        Coroutines.main {
            try {
                val roomBookingResponse = repository.bookingRoom(
                    "",prefManager.spNim!!,prefManager.spNama!!,prefManager.spNamaRoom!!,tanggal!!,jamMulai!!,jamSelesai!!,keterangan!!)
                roomBookingResponse.status?.let {
                    bookingRoomListener?.onSucces(roomBookingResponse.message!!)
                    return@main
                }
                bookingRoomListener?.onFailure(roomBookingResponse.message!!)
            }catch (e: ApiException){
                bookingRoomListener?.onFailure(e.message!!)
            }catch (e: NoInternetException) {
                bookingRoomListener?.onFailure(e.message!!)
            }
        }
    }

    val detailRoom = repository.getDetail()
}