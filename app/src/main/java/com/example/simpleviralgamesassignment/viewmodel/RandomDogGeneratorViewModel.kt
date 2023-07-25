package com.example.simpleviralgamesassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleviralgamesassignment.model.network.RandomDogGeneratorResponse
import com.example.simpleviralgamesassignment.model.network.RandomDogGeneratorApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RandomDogGeneratorViewModel : ViewModel() {
    private val randomDogApiInstance = RandomDogGeneratorApiInstance()
    private val _randomDogImageResponse = MutableLiveData<RandomDogGeneratorResponse>()
    val randomDogImageResponse: LiveData<RandomDogGeneratorResponse> get() = _randomDogImageResponse
    private val TAG = "RandomDogTag"

     fun getRandomDogImageFromAPI() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val response = randomDogApiInstance.getRandomDogImageFromAPI()
                _randomDogImageResponse.postValue(response.body())
            } catch (e: Exception) {
                Log.e(TAG, "getRandomDogImageFromAPI: ${e.message}", )
            }
        }
    }
}