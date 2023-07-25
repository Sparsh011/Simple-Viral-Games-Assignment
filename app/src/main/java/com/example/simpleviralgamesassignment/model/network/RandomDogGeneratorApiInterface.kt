package com.example.simpleviralgamesassignment.model.network

import com.example.simpleviralgamesassignment.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface RandomDogGeneratorApiInterface {
    @GET(Constants.RANDOM_DOG_GENERATOR_API_ENDPOINT)
    suspend fun getRandomDogImage() : Response<RandomDogGeneratorResponse>
}