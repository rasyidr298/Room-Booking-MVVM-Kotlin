package com.rrdev.roombookingmvvm.data.db.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

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
    val deskripsi: String,
    val image: String
)