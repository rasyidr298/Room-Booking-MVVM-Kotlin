package com.rrdev.roombookingmvvm.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rrdev.roombookingmvvm.data.db.entities.CURRENT_PROFILE_ID
import com.rrdev.roombookingmvvm.data.db.entities.Profile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: Profile) : Long

    @Query("SELECT * FROM Profile WHERE pid = $CURRENT_PROFILE_ID")
    fun getProfile() : LiveData<Profile>
}