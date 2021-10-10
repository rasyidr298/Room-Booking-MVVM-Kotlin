package com.rrdev.roombookingmvvm.data.network.responses

import com.google.gson.annotations.SerializedName

data class MyBookingResponse(
    @SerializedName("data")
    val `myBooking`: MyBooking?,
    val status: Boolean?,
    val text: String?
){
    data class MyBooking(
        val idBooking: String,
        val jamMulai: String,
        val jamSelesai: String,
        val keterangan: String,
        val namaPembooking: String,
        val namaRuangBooking: String,
        val nimBooking: String,
        val statusBooking: String,
        val tanggal: String
    )
}