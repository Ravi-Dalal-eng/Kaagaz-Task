package com.messaging.kaagaztask.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val albumName: String,
    val photoName: String
)