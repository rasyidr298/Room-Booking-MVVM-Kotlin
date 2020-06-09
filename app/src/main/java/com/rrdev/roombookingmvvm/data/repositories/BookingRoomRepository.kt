package com.rrdev.roombookingmvvm.data.repositories
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.SafeApiRequest
import com.rrdev.roombookingmvvm.data.network.responses.BookingRoomResponse

class BookingRoomRepository(
    private val api: MyApi
) :SafeApiRequest() {

    suspend fun bookingRoom(
        idBooking: String,
        nimBooking: String,
        namaPembooking: String,
        namaRuangBooking: String,
        tanggal: String,
        jamMulai: String,
        jamSelesai: String,
        keterangan: String
    ): BookingRoomResponse{
        return apiRequest { api.bookigRoom(idBooking,nimBooking,namaPembooking,namaRuangBooking,tanggal,jamMulai,jamSelesai,keterangan) }
    }
}