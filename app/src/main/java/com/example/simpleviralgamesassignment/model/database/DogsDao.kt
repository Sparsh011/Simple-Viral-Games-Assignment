package com.example.simpleviralgamesassignment.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simpleviralgamesassignment.model.entities.CachedDogsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogsDao {
    @Query("SELECT * FROM cached_dogs_table ORDER BY timestamp DESC")
    fun getCachedDogs(): Flow<List<CachedDogsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogInCache(dog: CachedDogsEntity) : Long

    @Query("DELETE FROM cached_dogs_table")
    suspend fun clearCachedDogs()

    @Query("SELECT COUNT() FROM cached_dogs_table WHERE id = :id")
    suspend fun checkCount(id: Long): Int

    @Query("DELETE FROM cached_dogs_table WHERE id = :id")
    suspend fun deleteItem(id: Long)
}