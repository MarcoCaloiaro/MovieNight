package com.marcocaloiaro.movienight.showscreen.model

import com.google.gson.annotations.SerializedName

data class RetrofitShowsResponse (
    @SerializedName("Search") val search : List<Show>?)