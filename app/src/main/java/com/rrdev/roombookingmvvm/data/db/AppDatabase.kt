package com.rrdev.roombookingmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rrdev.roombookingmvvm.data.db.dao.RoomDao
import com.rrdev.roombookingmvvm.data.db.dao.UserDao
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import com.rrdev.roombookingmvvm.data.db.entities.User

@Database(
    entities = [User::class, Rooms::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getRoomDao(): RoomDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance  ?: builDatabase(context).also {
                instance = it
            }
        }

        private fun builDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MyDatabase.db"
        ).build()
    }
}