package com.marcocaloiaro.movienight.details.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "shows")
@Parcelize
data class ShowDetails (
    @PrimaryKey @SerializedName("imdbID") val showId: String,
    @ColumnInfo(name = "movie_title") @SerializedName("Title") val title : String,
    @ColumnInfo(name = "released") @SerializedName("Released") val released : String,
    @ColumnInfo(name = "movie_genre") @SerializedName("Genre") val genre : String,
    @ColumnInfo(name = "movie_director") @SerializedName("Director") val director : String,
    @ColumnInfo(name = "movie_actors") @SerializedName("Actors") val actors : String,
    @ColumnInfo(name = "movie_plot") @SerializedName("Plot") val plot : String,
    @ColumnInfo(name= "movie_poster") @SerializedName("Poster") val poster : String,
    @ColumnInfo(name = "movie_rating") @SerializedName("imdbRating") val imdbRating : Double) : Parcelable