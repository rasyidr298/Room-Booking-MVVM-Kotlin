package com.rrdev.roombookingmvvm.data.network.responses

data class MyBookingResponse(
    val `data`: Data?,
    val status: Boolean?,
    val text: String?
){
    data class Data(
        val idBooking: String?,
        val jamMulai: String?,
        val jamSelesai: String?,
        val keterangan: String?,
        val namaPembooking: String?,
        val namaRuangBooking: String?,
        val nimBooking: String?,
        val statusBooking: String?,
        val tanggal: String?
    )
}