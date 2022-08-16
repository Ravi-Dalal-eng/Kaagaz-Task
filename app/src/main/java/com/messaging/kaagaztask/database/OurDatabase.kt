package com.messaging.kaagaztask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.messaging.kaagaztask.dao.AlbumsDao
import com.messaging.kaagaztask.dao.PhotosDao
import com.messaging.kaagaztask.entities.Album
import com.messaging.kaagaztask.entities.Photo

@Database(entities = [Photo::class, Album::class], version = 1)
abstract class OurDatabase: RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun photosDao(): PhotosDao
    companion object{
        @Volatile
        private var INSTANCE:OurDatabase?=null

        fun getDatabase(context: Context):OurDatabase{
            if(INSTANCE==null){
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(context,
                        OurDatabase::class.java,
                        "myDatabase").build()
                }
            }
            return INSTANCE!!
        }
    }
}