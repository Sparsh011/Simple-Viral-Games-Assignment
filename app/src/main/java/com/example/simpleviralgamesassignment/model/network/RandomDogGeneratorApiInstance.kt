package com.example.simpleviralgamesassignment.model.network

import com.example.simpleviralgamesassignment.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomDogGeneratorApiInstance {
    private val retrofitInstanceForRandomDogAPI = Retrofit.Builder()
        .baseUrl(Constants.RANDOM_DOG_GENERATOR_API_BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RandomDogGeneratorApiInterface::class.java)

    suspend fun getRandomDogImageFromAPI() : Response<RandomDogGeneratorResponse> {
        return retrofitInstanceForRandomDogAPI.getRandomDogImage()
    }
}