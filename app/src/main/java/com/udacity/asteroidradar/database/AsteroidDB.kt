package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Constants

@Database(version = 1,entities = [AsteroidEntity::class])
abstract class AsteroidDB : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    companion object {
        @Volatile
        private lateinit var instance: AsteroidDB
        fun getInstance(context: Context): AsteroidDB {
            synchronized(AsteroidDB::class.java) {
                if(!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDB::class.java,
                        Constants.DATABASE_NAME)
                        .build()
                }
            }
            return instance
        }
    }
}