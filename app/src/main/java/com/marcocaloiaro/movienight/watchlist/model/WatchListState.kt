package com.marcocaloiaro.movienight.watchlist.model

import com.marcocaloiaro.movienight.details.model.ShowDetails

sealed class WatchListState {
    object Idle : WatchListState()
    object Loading : WatchListState()
    data class Shows(val shows: List<ShowDetails>) : WatchListState()
    object ShowRemoved : WatchListState()
    data class Error(val error: String?) : WatchListState()
}
