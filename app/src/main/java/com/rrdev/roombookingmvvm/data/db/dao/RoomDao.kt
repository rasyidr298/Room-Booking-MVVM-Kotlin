package com.rrdev.roombookingmvvm.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rrdev.roombookingmvvm.data.db.entities.DetailRooms
import com.rrdev.roombookingmvvm.data.db.entities.Rooms

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllRoom(rooms: List<Rooms>)

    @Query("SELECT * FROM Rooms")
    fun getRoom() : LiveData<List<Rooms>>

    @Query("SELECT * FROM Rooms WHERE namaRoom=:namaRoom")
    fun getDetailRoom(namaRoom: String) : LiveData<DetailRooms>
}