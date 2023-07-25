package com.example.simpleviralgamesassignment.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.simpleviralgamesassignment.model.entities.CachedDogsEntity
import com.example.simpleviralgamesassignment.repository.DogsRepository
import kotlinx.coroutines.launch

class CachedDogsViewModel(private val repository: DogsRepository) : ViewModel() {

    private val _recentDogsLiveData = MutableLiveData<List<CachedDogsEntity>>()
    val recentDogsLiveData: LiveData<List<CachedDogsEntity>> get() = _recentDogsLiveData
    private val TAG = "CachedViewModelTagg"

    init {
        viewModelScope.launch {
            repository.getCachedDogs().collect { cachedDogs ->
                _recentDogsLiveData.value = cachedDogs
            }
        }
    }

    fun insertDogToCache(dog: CachedDogsEntity, callback : (Long) -> Unit) {
        viewModelScope.launch {
            val id = repository.insertDogInCache(dog)
            callback(id)
            Log.d(TAG, "insertDogToCache: ${dog.imageUrl} and ${dog.timestamp}")
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            repository.clearCachedDogs()
        }
    }

    suspend fun checkCount(id: Long) : Int {
        return repository.checkCount(id)
    }

    suspend fun deleteItem(id: Long) {
        repository.deleteItem(id)
    }
}

class CachedDogsViewModelFactory(private val repository: DogsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CachedDogsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CachedDogsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}


