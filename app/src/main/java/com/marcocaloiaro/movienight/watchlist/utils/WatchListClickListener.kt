package com.marcocaloiaro.movienight.watchlist.utils

import com.marcocaloiaro.movienight.details.model.ShowDetails

interface WatchListClickListener {
    fun onShowClicked(show: ShowDetails)
    fun onDeleteIconClicked(shows: List<ShowDetails>, show: ShowDetails)
}