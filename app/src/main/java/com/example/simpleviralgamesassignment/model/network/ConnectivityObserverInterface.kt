package com.example.simpleviralgamesassignment.model.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserverInterface {
    fun observeConnection() : Flow<Status>

    enum class Status {
        Available, Unavailable, Lost
    }
}