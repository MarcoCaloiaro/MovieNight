package com.marcocaloiaro.movienight.base.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marcocaloiaro.movienight.details.model.ShowDetails

@Database(entities = [ShowDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun showsDao() : ShowsDao
}