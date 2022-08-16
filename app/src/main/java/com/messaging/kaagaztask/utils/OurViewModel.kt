package com.messaging.kaagaztask.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messaging.kaagaztask.entities.Album
import com.messaging.kaagaztask.entities.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OurViewModel(private val ourRepository:OurRepository): ViewModel() {

    fun addAlbum(album: Album){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.addAlbum(album)
        }
    }

    fun getAlbums(): LiveData<List<Album>> {
        return ourRepository.getAlbums()
    }

    fun addPhoto(photo: Photo){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.addPhoto(photo)
        }
    }

    fun getPhotosByAlbumName(albumName:String): LiveData<List<Photo>> {
        return ourRepository.getPhotoByAlbumName(albumName)
    }
}