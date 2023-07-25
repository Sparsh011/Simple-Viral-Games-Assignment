package com.example.simpleviralgamesassignment.repository

import com.example.simpleviralgamesassignment.model.database.DogsDao
import com.example.simpleviralgamesassignment.model.entities.CachedDogsEntity
import kotlinx.coroutines.flow.Flow

class DogsRepository(private val dao: DogsDao) {

    fun getCachedDogs(): Flow<List<CachedDogsEntity>> {
        return dao.getCachedDogs()
    }

    suspend fun insertDogInCache(dog: CachedDogsEntity): Long {
        return dao.insertDogInCache(dog)
    }

    suspend fun clearCachedDogs() {
        dao.clearCachedDogs()
    }

    suspend fun checkCount(id: Long) : Int {
        return dao.checkCount(id)
    }

    suspend fun deleteItem(id: Long) {
        dao.deleteItem(id)
    }
}