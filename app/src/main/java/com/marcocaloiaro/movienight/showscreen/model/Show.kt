package com.marcocaloiaro.movienight.showscreen.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show (@SerializedName("Title") val title : String,
                   @SerializedName("imdbID") val imdbID : String,
                   @SerializedName("Type") val type : String,
                   @SerializedName("Poster") val poster : String) : Parcelable
