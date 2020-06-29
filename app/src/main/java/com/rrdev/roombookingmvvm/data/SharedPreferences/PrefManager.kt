package com.rrdev.roombookingmvvm.data.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

private const val PREFS_NAME = "booking_apps"
private const val NIM_USER = "key_nim"
private const val NAMA_USER = "key_nama"
private const val NAMA_ROOM = "key_nama_room"
private const val ID_BOOKING = "key_id_booking"
private const val TOKEN = "key_token"

class PrefManager(context: Context) {

    private val sp: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    val spe: SharedPreferences.Editor by lazy {
        sp.edit()
    }

    fun clear() {
        sp.edit().clear().apply()
    }

    fun clearIdBooking(){
        spe.remove(ID_BOOKING)
    }

    //store Nim
    var spNim: String?
        get() = sp.getString(NIM_USER, "")
        set(value) {
            spe.putString(NIM_USER, value)
            spe.commit()
        }

    //store nama
    var spNama: String?
        get() = sp.getString(NAMA_USER, "")
        set(value) {
            spe.putString(NAMA_USER, value)
            spe.commit()
        }

    var spNamaRoom: String?
        get() = sp.getString(NAMA_ROOM, "")
        set(value) {
            spe.putString(NAMA_ROOM, value)
            spe.commit()
        }

    //store IdBooking
    var spIdBooking: String?
        get() = sp.getString(ID_BOOKING, "")
        set(value) {
            spe.putString(ID_BOOKING, value)
            spe.commit()
        }

    //store token
    var spToken: String?
        get() = sp.getString(TOKEN, null)
        set(value) {
            spe.putString(TOKEN, value)
            spe.commit()
        }
}
