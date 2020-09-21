package com.marcocaloiaro.movienight.base.network

import com.marcocaloiaro.movienight.base.local.ShowsDao
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.showscreen.model.RetrofitShowsResponse
import javax.inject.Inject

class ShowsRepository @Inject constructor(private val showsApiService: ShowsApiService, private val showsDao: ShowsDao) {

    suspend fun fetchShows(omdbKey: String, searchType: String, showTitle: String) : RetrofitShowsResponse = showsApiService.getShows(omdbKey, showTitle, searchType)

    suspend fun fetchShowDetails(omdbKey: String, showId: String, plotType: String) : ShowDetails? = showsApiService.getShowDetails(omdbKey, showId, plotType)

    suspend fun storeShowInDatabase(showDetails: ShowDetails) = showsDao.addShow(showDetails)

    suspend fun fetchWatchList() = showsDao.getShows()

    suspend fun removeShowFromDatabase(showDetails: ShowDetails) = showsDao.delete(showDetails)
}