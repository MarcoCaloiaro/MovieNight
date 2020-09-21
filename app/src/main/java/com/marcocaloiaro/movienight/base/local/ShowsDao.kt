package com.marcocaloiaro.movienight.base.local

import androidx.room.*
import com.marcocaloiaro.movienight.details.model.ShowDetails

@Dao
interface ShowsDao {

    @Query("SELECT * FROM shows")
    suspend fun getShows() : List<ShowDetails>

    @Delete
    suspend fun delete(showDetails: ShowDetails)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addShow(showDetails: ShowDetails)

}