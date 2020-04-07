package com.rrdev.mvvmtrial.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rrdev.mvvmtrial.data.db.entities.User
import okhttp3.internal.Internal.instance

@Database(
    entities = [User::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao():UserDao

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