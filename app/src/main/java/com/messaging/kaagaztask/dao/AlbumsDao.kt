package com.messaging.kaagaztask.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.messaging.kaagaztask.entities.Album


@Dao
interface AlbumsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlbum(album: Album)

    @Query("SELECT * FROM album")
    fun getAlbums(): LiveData<List<Album>>

}