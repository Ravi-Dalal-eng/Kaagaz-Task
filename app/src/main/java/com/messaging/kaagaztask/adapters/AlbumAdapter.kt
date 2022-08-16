package com.messaging.kaagaztask.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.messaging.kaagaztask.activities.PreviewActivity
import com.messaging.kaagaztask.databinding.AlbumItemBinding
import com.messaging.kaagaztask.entities.Album


class AlbumAdapter(private val context: Context,private val albumList:List<Album>):
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumAdapter.ViewHolder {
        val binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumList[position]
        holder.itemBinding.albumName.setText(album.albumName)
        holder.itemBinding.albumName.setOnClickListener {
            val intent=Intent(context,PreviewActivity::class.java)
            intent.putExtra("albumName",album.albumName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = albumList.size

    class ViewHolder(val itemBinding: AlbumItemBinding, ) :
        RecyclerView.ViewHolder(itemBinding.root)

}