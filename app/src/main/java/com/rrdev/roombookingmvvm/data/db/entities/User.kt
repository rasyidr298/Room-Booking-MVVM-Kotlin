package com.rrdev.roombookingmvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User(
    val idUsers: String?= null,
    val nim: String?= null,
    val namaUser: String?= null,
    val nohp: String?= null,
    val password: String?= null,
    val token: String?= null,
    val image: String?= null
)
{
    //hanya menyimpan 1 user saja
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}

