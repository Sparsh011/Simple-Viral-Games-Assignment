package com.example.simpleviralgamesassignment.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_dogs_table")
data class CachedDogsEntity(
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long = 0,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)

