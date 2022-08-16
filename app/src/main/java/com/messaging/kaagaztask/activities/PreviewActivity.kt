package com.messaging.kaagaztask.activities

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.messaging.kaagaztask.database.OurDatabase
import com.messaging.kaagaztask.databinding.ActivityPreviewBinding
import com.messaging.kaagaztask.entities.Photo
import com.messaging.kaagaztask.utils.OurRepository
import com.messaging.kaagaztask.utils.OurViewModel
import com.messaging.kaagaztask.utils.OurViewModelFactory
import java.io.File
import java.text.FieldPosition
import kotlin.properties.Delegates

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private lateinit var ourViewModel: OurViewModel
    private lateinit var photoList:ArrayList<Photo>
    private var photoNumber=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val albumName=intent.getStringExtra("albumName")
        initialize()
        ourViewModel.getPhotosByAlbumName(albumName!!).observe(this){
            photoList.clear()
            photoList.addAll(it)
            loadPhoto(photoNumber)
            if(photoList.size>1)
                binding.nextButton.visibility=View.VISIBLE
        }
        binding.nextButton.setOnClickListener {
            binding.previousButton.visibility=View.VISIBLE
            photoNumber++
            loadPhoto(photoNumber)
            if(photoNumber.equals(photoList.size-1))
                binding.nextButton.visibility=View.INVISIBLE
        }
        binding.previousButton.setOnClickListener {
           binding.nextButton.visibility=View.VISIBLE
            photoNumber--
            loadPhoto(photoNumber)
            if(photoNumber.equals(0))
                binding.previousButton.visibility=View.INVISIBLE
        }
    }

    private fun loadPhoto(position:Int) {
        val file = File(externalMediaDirs[0],photoList[position].photoName)
        Glide.with(binding.root).load(file).into(binding.albumPhotos)
    }

    private fun initialize() {
        val ourDatabase= OurDatabase.getDatabase(applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        ourViewModel= ViewModelProvider(this, OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)
        photoList= ArrayList()
    }
}