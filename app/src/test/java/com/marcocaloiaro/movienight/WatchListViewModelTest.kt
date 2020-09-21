package com.marcocaloiaro.movienight

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcocaloiaro.movienight.base.network.ShowsRepository
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.utils.TestCoroutineRule
import com.marcocaloiaro.movienight.watchlist.model.WatchListState
import com.marcocaloiaro.movienight.watchlist.viewmodel.WatchListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WatchListViewModelTest {

    companion object {
        const val ERROR_FETCHING_MESSAGE = "There was an error fetching show details."
        const val ERROR_REMOVING_MESSAGE = "There was an error storing show."
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: ShowsRepository

    @Mock
    private lateinit var showDetailsMock: List<ShowDetails>


    @Test
    fun givenServerResponse200_whenFetchWatchList_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(showDetailsMock)
                .`when`(repository)
                .fetchWatchList()
            val viewModel = WatchListViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is WatchListState.Idle)
            viewModel.fetchWatchList()
            Mockito.verify(repository).fetchWatchList()
            assert(viewModel.state.value is WatchListState.Shows)
        }
    }

    @Test
    fun givenServerResponseError_whenFetchShowDetails_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage =
                ERROR_FETCHING_MESSAGE
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(repository).fetchWatchList()
            val viewModel = WatchListViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is WatchListState.Idle)
            viewModel.fetchWatchList()
            Mockito.verify(repository).fetchWatchList()
            assert(viewModel.state.value is WatchListState.Error)
        }
    }

    @Test
    fun givenRoomPositiveResponse_whenRemovingValue_shouldReturnSuccess(){
        testCoroutineRule.runBlockingTest {
            val showDetails = ShowDetails("", "",
                "", "", "",
                "", "", "", 1.0)
            Mockito.doReturn(showDetailsMock)
                .`when`(repository).removeShowFromDatabase(showDetails)
            val viewModel = WatchListViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is WatchListState.Idle)
            viewModel.removeShowFromDatabase(showDetails)
            Mockito.verify(repository).removeShowFromDatabase(showDetails)
            assert(viewModel.state.value is WatchListState.ShowRemoved)
        }
    }

    @Test
    fun givenRoomErrorResponse_whenRemovingValue_shouldReturnError(){
        testCoroutineRule.runBlockingTest {
            val errorMessage = ERROR_REMOVING_MESSAGE
            val showDetails = ShowDetails("", "",
                "", "", "",
                "", "", "", 1.0)
            Mockito.doThrow(RuntimeException())
                .`when`(repository)
                .removeShowFromDatabase(showDetails)
            val viewModel = WatchListViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is WatchListState.Idle)
            viewModel.removeShowFromDatabase(showDetails)
            Mockito.verify(repository).removeShowFromDatabase(showDetails)
            assert(viewModel.state.value is WatchListState.Error)
        }
    }

}