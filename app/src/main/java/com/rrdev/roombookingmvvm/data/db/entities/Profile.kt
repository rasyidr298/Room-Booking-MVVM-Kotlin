package com.rrdev.roombookingmvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


const val CURRENT_PROFILE_ID = 0

@Entity
data class Profile(
    var idUsers: String,
    var nim: String,
    var namaUser: String,
    var nohp: String,
    var password: String,
    var image: String
){
    //hanya menyimpan 1 user saja
    @PrimaryKey(autoGenerate = false)
    var pid: Int = CURRENT_PROFILE_ID
}
