package com.messaging.kaagaztask.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.messaging.kaagaztask.entities.Photo


@Dao
interface PhotosDao {
    @Insert
    suspend fun addPhoto(photo: Photo)

    @Query("SELECT * FROM photo WHERE albumName=:albumName ")
    fun getPhotos(albumName:String): LiveData<List<Photo>>

}