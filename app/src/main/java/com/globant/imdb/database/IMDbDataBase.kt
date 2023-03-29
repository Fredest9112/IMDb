package com.globant.imdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.globant.imdb.data.Constants.DATABASE_NAME

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class IMDbDataBase : RoomDatabase() {
    abstract val imDbDao: IMDbDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: IMDbDataBase

        fun getInstance(context: Context): IMDbDataBase {
            synchronized(IMDbDataBase::class.java) {
                if(!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        IMDbDataBase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}