package com.messaging.kaagaztask.utils

import androidx.lifecycle.LiveData
import com.messaging.kaagaztask.database.OurDatabase
import com.messaging.kaagaztask.entities.Album
import com.messaging.kaagaztask.entities.Photo

class OurRepository(private val ourDatabase: OurDatabase) {
    suspend fun addAlbum(album: Album){
        ourDatabase.albumsDao().addAlbum(album)
    }
    fun getAlbums(): LiveData<List<Album>> {
        return ourDatabase.albumsDao().getAlbums()
    }
  suspend fun addPhoto(photo: Photo){
      ourDatabase.photosDao().addPhoto(photo)
  }
    fun getPhotoByAlbumName(albumName:String): LiveData<List<Photo>> {
        return ourDatabase.photosDao().getPhotos(albumName)
    }

}