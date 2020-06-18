package com.rrdev.roombookingmvvm.data.db.entities

data class DetailRooms(
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