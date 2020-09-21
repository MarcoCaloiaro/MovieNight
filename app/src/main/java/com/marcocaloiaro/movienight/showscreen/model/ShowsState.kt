package com.marcocaloiaro.movienight.showscreen.model

sealed class ShowsState {
    object Idle : ShowsState()
    object Loading : ShowsState()
    data class Shows(val movies: List<Show>) : ShowsState()
    data class Error(val error: String?) : ShowsState()

}
