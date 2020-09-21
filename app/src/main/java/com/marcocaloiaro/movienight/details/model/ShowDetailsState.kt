package com.marcocaloiaro.movienight.details.model

sealed class ShowDetailsState {
    object Idle : ShowDetailsState()
    object Loading : ShowDetailsState()
    data class ShowDetails(val showDetails: com.marcocaloiaro.movienight.details.model.ShowDetails) : ShowDetailsState()
    object ShowStored : ShowDetailsState()
    data class Error(val error: String?) : ShowDetailsState()
}