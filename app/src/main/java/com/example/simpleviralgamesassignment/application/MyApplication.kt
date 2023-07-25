package com.example.simpleviralgamesassignment.application

import android.app.Application
import com.example.simpleviralgamesassignment.model.database.DogsDatabase
import com.example.simpleviralgamesassignment.repository.DogsRepository

class MyApplication : Application(){
    private val database by lazy{
        DogsDatabase.getCacheDatabase(this@MyApplication)
    }
    val repository by lazy {
        DogsRepository(database.dao)
    }
}