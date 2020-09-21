package com.marcocaloiaro.movienight.base.intent

import com.marcocaloiaro.movienight.details.model.ShowDetails

sealed class MainIntent {
    data class FetchMovies(val key: String, val type: String, val title: String) : MainIntent()
    data class FetchSeries(val key: String, val type: String, val title: String) : MainIntent()
    data class FetchShowDetails(val key:String, val showId: String, val plotType: String) : MainIntent()
    data class StoreShowInDatabase(val showDetails: ShowDetails) : MainIntent()
    object FetchWatchListMovies : MainIntent()
    data class RemoveShowFromDatabase(val showDetails: ShowDetails) : MainIntent()
}