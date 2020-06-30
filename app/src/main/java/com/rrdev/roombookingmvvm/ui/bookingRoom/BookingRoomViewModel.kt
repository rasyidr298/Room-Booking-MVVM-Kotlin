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

        val token = prefManager.spToken
        if (token.isNullOrEmpty()){
            bookingRoomListener?.onFailure("Gagal, Coba kembali..")
            return
        }

        Coroutines.main {
            try {
                repository.updateToken(prefManager.spNim!!, prefManager.spToken!!, prefManager.spNim!!)
            }catch (e: NoInternetException) {
                bookingRoomListener?.onFailure(e.message!!)
            }
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
            }catch (e: ApiException){
                bookingRoomListener?.onFailure(e.message!!)
            }catch (e: NoInternetException) {
                bookingRoomListener?.onFailure(e.message!!)
            }
        }
    }

    val detailRoom = repository.getDetail()
}