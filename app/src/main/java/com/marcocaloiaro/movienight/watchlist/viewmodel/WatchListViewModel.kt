package com.marcocaloiaro.movienight.watchlist.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcocaloiaro.movienight.base.di.AppModule
import com.marcocaloiaro.movienight.base.intent.MainIntent
import com.marcocaloiaro.movienight.base.network.ShowsRepository
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.watchlist.model.WatchListState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

@ExperimentalCoroutinesApi
class WatchListViewModel @ViewModelInject constructor(private val repository: ShowsRepository, @AppModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<WatchListState>(WatchListState.Idle)
    val state: StateFlow<WatchListState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.FetchWatchListMovies -> fetchWatchList()
                    is MainIntent.RemoveShowFromDatabase -> removeShowFromDatabase(it.showDetails)
                }
            }
        }
    }

    fun removeShowFromDatabase(showDetails: ShowDetails) {
        viewModelScope.launch(ioDispatcher) {
            try {
                _state.value = WatchListState.Loading
                repository.removeShowFromDatabase(showDetails)
                _state.value = WatchListState.ShowRemoved
            } catch (e: Exception) {
                _state.value = WatchListState.Error(e.localizedMessage)
            }
        }
    }

    fun fetchWatchList() {
        viewModelScope.launch(ioDispatcher) {
            _state.value = WatchListState.Loading
            try {
                _state.value = WatchListState.Shows(repository.fetchWatchList())
            } catch (e: Exception) {
                _state.value = WatchListState.Error(e.localizedMessage)
            }
        }
    }
}