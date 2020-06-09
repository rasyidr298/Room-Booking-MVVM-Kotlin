package com.rrdev.roombookingmvvm.data.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//menyimpan user yang sama
const val CURRENT_USER_ID = 0

@Entity
data class User(
    val idUsers: String,
    val nim: String,
    val namaUser: String,
    val nohp: String,
    val password: String,
    val token: String,
    val image: String
)
{
    //hanya menyimpan 1 user saja
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}

