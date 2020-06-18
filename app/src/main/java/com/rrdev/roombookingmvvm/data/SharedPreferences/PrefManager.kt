package com.rrdev.roombookingmvvm.data.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

private const val PREFS_NAME = "booking_apps"
private const val NIM_USER = "key_nim"
private const val NAMA_USER = "key_nama"
private const val NAMA_ROOM = "key_nama_room"

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

    var spNim: String?
        get() = sp.getString(NIM_USER, "")
        set(value) {
            spe.putString(NIM_USER, value)
            spe.commit()
        }

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
}
