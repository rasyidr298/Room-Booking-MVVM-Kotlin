package com.rrdev.mvvmtrial.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rrdev.roombookingmvvm.data.db.entities.CURRENT_USER_ID
import com.rrdev.roombookingmvvm.data.db.entities.User

@Dao
interface UserDao {
    //menyimpan dan menimpa data user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User) : Long

    @Query("SELECT * FROM User WHERE uid = $CURRENT_USER_ID")
    fun getUser() : LiveData<User>
}