package com.messaging.kaagaztask.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "album",indices = arrayOf(Index(value = ["albumName"], unique = true)))
data class Album(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val albumName:String
)