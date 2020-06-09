package com.rrdev.roombookingmvvm.data.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Booking(
    @PrimaryKey(autoGenerate = false)
    val idBooking: String,
    val namaPembooking: String,
    val namaRuangBooking: String,
    val tanggal: String,
    val jamMulai: String,
    val jamSelesai: String,
    val keterangan: String,
    val statusBooking: String
)