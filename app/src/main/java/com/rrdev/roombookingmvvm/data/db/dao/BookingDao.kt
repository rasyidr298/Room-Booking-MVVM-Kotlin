package com.rrdev.roombookingmvvm.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rrdev.roombookingmvvm.data.db.entities.Booking

@Dao
interface BookingDao {
    //menyimpan dan menimpa data user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBooking(booking: List<Booking>)

    @Query("SELECT * FROM booking")
    fun getBooking() : LiveData<List<Booking>>
}