package com.marcocaloiaro.movienight

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcocaloiaro.movienight.base.network.ShowsRepository
import com.marcocaloiaro.movienight.showscreen.base.viewmodel.ShowsViewModel
import com.marcocaloiaro.movienight.showscreen.model.RetrofitShowsResponse
import com.marcocaloiaro.movienight.showscreen.model.ShowsState
import com.marcocaloiaro.movienight.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ShowsViewModelTest {

    companion object {
        const val ERROR_MESSAGE = "There was an error fetching shows."
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: ShowsRepository

    @Mock
    private lateinit var retrofitShowsResponse: RetrofitShowsResponse


    @Test
    fun givenServerResponse200_whenFetchShows_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(retrofitShowsResponse)
                .`when`(repository)
                .fetchShows(
                    ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
                    ArgumentMatchers.anyString())
            val viewModel = ShowsViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is ShowsState.Idle)
            viewModel.fetchShows("", "", "")
            Mockito.verify(repository).fetchShows(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())
            assert(viewModel.state.value is ShowsState.Shows)
        }
    }

    @Test
    fun givenServerResponseError_whenFetchRestaurants_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = ERROR_MESSAGE
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(repository).fetchShows(
                    ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
                    ArgumentMatchers.anyString())
            val viewModel = ShowsViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is ShowsState.Idle)
            viewModel.fetchShows("", "", "")
            Mockito.verify(repository).fetchShows(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())
            assert(viewModel.state.value is ShowsState.Error)
        }
    }

}