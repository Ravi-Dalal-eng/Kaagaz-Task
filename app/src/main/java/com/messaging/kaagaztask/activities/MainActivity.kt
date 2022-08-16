package com.messaging.kaagaztask.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.messaging.kaagaztask.adapters.AlbumAdapter
import com.messaging.kaagaztask.database.OurDatabase
import com.messaging.kaagaztask.databinding.ActivityMainBinding
import com.messaging.kaagaztask.entities.Album
import com.messaging.kaagaztask.utils.OurRepository
import com.messaging.kaagaztask.utils.OurViewModel
import com.messaging.kaagaztask.utils.OurViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ourViewModel: OurViewModel
    private lateinit var albumList:ArrayList<Album>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      initialize()
        binding.albumsRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.albumsRecyclerView.adapter=AlbumAdapter(this,albumList)

        ourViewModel.getAlbums().observe(this){

            albumList.clear()
            albumList.addAll(it)
            if(!albumList.isEmpty())
                binding.showMessage.visibility=View.GONE
            (binding.albumsRecyclerView.adapter as AlbumAdapter).notifyDataSetChanged()

        }

        binding.takePhotoButton.setOnClickListener {
             val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialize() {
        val ourDatabase= OurDatabase.getDatabase(applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        ourViewModel=ViewModelProvider(this,OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)
        albumList= ArrayList()
    }
}