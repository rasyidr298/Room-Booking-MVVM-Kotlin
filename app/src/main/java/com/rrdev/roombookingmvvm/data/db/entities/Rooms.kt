package com.rrdev.roombookingmvvm.data.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//menyimpan user yang sama
//const val CURRENT_ROOM_ID = 0

@Entity
data class Rooms(
    @PrimaryKey(autoGenerate = false)
    val idRoom: Int,
    val namaRoom: String,
    val kapasitas: Int,
    val fasilitas1: String,
    val fasilitas2: String,
    val fasilitas3: String,
    val fasilitas4: String,
    val deskripsi: String
)
//{
//    //hanya menyimpan 1 user saja
//    @PrimaryKey(autoGenerate = false)
//    var rid: Int = CURRENT_ROOM_ID
//}