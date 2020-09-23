package com.marcocaloiaro.movienight.showscreen.base.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcocaloiaro.movienight.base.di.AppModule
import com.marcocaloiaro.movienight.base.intent.MainIntent
import com.marcocaloiaro.movienight.showscreen.model.ShowsState
import com.marcocaloiaro.movienight.base.network.ShowsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

@ExperimentalCoroutinesApi
class ShowsViewModel @ViewModelInject constructor(private val repository: ShowsRepository, @AppModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<ShowsState>(
        ShowsState.Idle
    )
    val state: StateFlow<ShowsState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.FetchMovies -> fetchShows(it.key, it.type, it.title)
                    is MainIntent.FetchSeries -> fetchShows(it.key, it.type, it.title)
                }
            }
        }
    }

    fun fetchShows(key: String, searchType: String, showTitle: String) {
        viewModelScope.launch(ioDispatcher) {
            _state.value = ShowsState.Loading
            try {
                repository.fetchShows(key, searchType, showTitle).search?.let {
                    _state.value = ShowsState.Shows(it)
                    return@launch
                }
                _state.value = ShowsState.Shows(emptyList())
            } catch (e: Exception) {
                _state.value = ShowsState.Error(e.localizedMessage)
            }

        }
    }

}