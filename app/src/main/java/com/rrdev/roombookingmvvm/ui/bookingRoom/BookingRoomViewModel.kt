package com.rrdev.roombookingmvvm.ui.bookingRoom

import android.view.View
import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.data.repositories.BookingRoomRepository
import com.rrdev.roombookingmvvm.util.ApiException
import com.rrdev.roombookingmvvm.util.Coroutines

class BookingRoomViewModel(
    private val repository: BookingRoomRepository
) : ViewModel() {
    var idBooking: String? = ""
    var nimBooking: String? = null
    var namaPembooking: String? = null
    var namaRuangBooking: String?= null
    var tanggal: String? = null
    var jamMulai: String? = null
    var jamSelesai: String? = null
    var keterangan: String?= null

    var bookingRoomListener: BookingRoomListener? = null

    fun onBookingButtonClick(view: View){
        bookingRoomListener?.onStarted()
        if ((nimBooking.isNullOrEmpty() ||
                namaPembooking.isNullOrEmpty() || namaRuangBooking.isNullOrEmpty() ||
                tanggal.isNullOrEmpty() || jamMulai.isNullOrEmpty() ||
                jamSelesai.isNullOrEmpty() || keterangan.isNullOrEmpty() )){
            bookingRoomListener?.onFailure("harus terisi semuaa")
            return
        }

        Coroutines.main {
            try {
                val roomBookingResponse = repository.bookingRoom(
                    idBooking!!,nimBooking!!,namaPembooking!!,namaRuangBooking!!,tanggal!!,jamMulai!!,jamSelesai!!,keterangan!!)
                roomBookingResponse.status?.let {
                    bookingRoomListener?.onSucces(roomBookingResponse.message!!)
                    return@main
                }
                bookingRoomListener?.onFailure(roomBookingResponse.message!!)
            }catch (e: ApiException){
                bookingRoomListener?.onFailure(e.message!!)
            }
        }

    }
}