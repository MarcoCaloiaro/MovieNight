package com.marcocaloiaro.movienight.base.network

import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.showscreen.model.RetrofitShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowsApiService {

    @GET("?")
    suspend fun getShows(@Query("apikey") apiKey : String,
                          @Query("s") showTitle : String,
                          @Query("type") showType : String) : RetrofitShowsResponse
    @GET("?")
    suspend fun getShowDetails(@Query("apikey") apiKey: String,
                               @Query("i") showId: String,
                               @Query("plot") plotType: String) : ShowDetails
}