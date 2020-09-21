package com.marcocaloiaro.movienight.details.viemodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcocaloiaro.movienight.base.di.AppModule
import com.marcocaloiaro.movienight.base.intent.MainIntent
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.details.model.ShowDetailsState
import com.marcocaloiaro.movienight.base.network.ShowsRepository
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
class ShowDetailsViewModel @ViewModelInject constructor(private val repository: ShowsRepository, @AppModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<ShowDetailsState>(ShowDetailsState.Idle)
    val state: StateFlow<ShowDetailsState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.FetchShowDetails -> fetchShowDetails(it.key, it.showId, it.plotType)
                    is MainIntent.StoreShowInDatabase -> storeShow(it.showDetails)
                }
            }
        }
    }

    fun storeShow(showDetails: ShowDetails) {
        viewModelScope.launch(ioDispatcher) {
            _state.value = ShowDetailsState.Loading
            try {
                repository.storeShowInDatabase(showDetails)
                _state.value = ShowDetailsState.ShowStored
            } catch (e: Exception) {
                _state.value = ShowDetailsState.Error(e.localizedMessage)
            }
        }
    }

    fun fetchShowDetails(key: String, showId: String, plotType: String) {
        viewModelScope.launch(ioDispatcher) {
            _state.value = ShowDetailsState.Loading
            try {
                repository.fetchShowDetails(key, showId, plotType)?.let {
                    _state.value = ShowDetailsState.ShowDetails(it)
                    return@launch
                }
                _state.value = ShowDetailsState.Error(null)
            } catch (e: Exception) {
                _state.value = ShowDetailsState.Error(e.localizedMessage)
            }
        }
    }

}