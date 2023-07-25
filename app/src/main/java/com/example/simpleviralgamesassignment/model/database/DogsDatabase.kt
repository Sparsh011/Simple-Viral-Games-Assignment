package com.example.simpleviralgamesassignment.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simpleviralgamesassignment.model.entities.CachedDogsEntity


@Database(entities = [CachedDogsEntity::class], version = 1)
abstract class DogsDatabase : RoomDatabase(){
    abstract val dao : DogsDao

    companion object {
        @Volatile
        private var INSTANCE: DogsDatabase? = null

        fun getCacheDatabase(context: Context): DogsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogsDatabase::class.java,
                    "cache_for_dogs"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}