package com.rrdev.roombookingmvvm.data.repositories
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.SafeApiRequest
import com.rrdev.roombookingmvvm.data.network.responses.BookingRoomResponse
import com.rrdev.roombookingmvvm.data.network.responses.UpdateTokenResponse

class BookingRoomRepository(
    private val api: MyApi,
    private val db:AppDatabase
) :SafeApiRequest() {

    suspend fun bookingRoom(
        idBooking: String, nimBooking: String, namaPembooking: String, namaRuangBooking: String,
        tanggal: String, jamMulai: String, jamSelesai: String, keterangan: String
    ): BookingRoomResponse{
        return apiRequest { api.bookigRoom(idBooking,nimBooking,namaPembooking,namaRuangBooking,tanggal,jamMulai,jamSelesai,keterangan) }
    }

    suspend fun updateToken(
        nimParams: String, token: String, nim: String
    ): UpdateTokenResponse{
        return apiRequest { api.updateToken(nimParams,token, nim) }
    }

    fun getDetail() = db.getRoomDao().getDetailRoom(prefManager.spNamaRoom!!)
}