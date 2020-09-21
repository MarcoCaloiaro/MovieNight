package com.marcocaloiaro.movienight

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcocaloiaro.movienight.base.network.ShowsRepository
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.details.model.ShowDetailsState
import com.marcocaloiaro.movienight.details.viemodel.ShowDetailsViewModel
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
class ShowDetailsViewModelTest {

    companion object {
        const val ERROR_FETCHING_MESSAGE = "There was an error fetching show details."
        const val ERROR_STORING_MESSAGE = "There was an error storing show."
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: ShowsRepository

    @Mock
    private lateinit var showDetailsMock: ShowDetails


    @Test
    fun givenServerResponse200_whenFetchShowDetails_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(showDetailsMock)
                .`when`(repository)
                .fetchShowDetails(
                    ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
                    ArgumentMatchers.anyString())
            val viewModel = ShowDetailsViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is ShowDetailsState.Idle)
            viewModel.fetchShowDetails("", "", "")
            Mockito.verify(repository).fetchShowDetails(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())
            assert(viewModel.state.value is ShowDetailsState.ShowDetails)
        }
    }

    @Test
    fun givenServerResponseError_whenFetchShowDetails_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage =
                ERROR_FETCHING_MESSAGE
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(repository).fetchShowDetails(
                    ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
                    ArgumentMatchers.anyString())
            val viewModel = ShowDetailsViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is ShowDetailsState.Idle)
            viewModel.fetchShowDetails("", "", "")
            Mockito.verify(repository).fetchShowDetails(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())
            assert(viewModel.state.value is ShowDetailsState.Error)
        }
    }

    @Test
    fun givenRoomPositiveResponse_whenStoringValue_shouldReturnSuccess(){
        testCoroutineRule.runBlockingTest {
            val showDetails = ShowDetails("", "",
                "", "", "",
                "", "", "", 1.0)
            Mockito.doReturn(showDetailsMock)
                .`when`(repository)
                .storeShowInDatabase(showDetails)
            val viewModel = ShowDetailsViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is ShowDetailsState.Idle)
            viewModel.storeShow(showDetails)
            Mockito.verify(repository).storeShowInDatabase(showDetails)
            assert(viewModel.state.value is ShowDetailsState.ShowStored)
        }
    }

    @Test
    fun givenRoomErrorResponse_whenStoringValue_shouldReturnError(){
        testCoroutineRule.runBlockingTest {
            val errorMessage = ERROR_STORING_MESSAGE
            val showDetails = ShowDetails("", "",
                "", "", "",
                "", "", "", 1.0)
            Mockito.doThrow(RuntimeException())
                .`when`(repository)
                .storeShowInDatabase(showDetails)
            val viewModel = ShowDetailsViewModel(repository,
                testCoroutineRule.testCoroutineDispatcher)
            assert(viewModel.state.value is ShowDetailsState.Idle)
            viewModel.storeShow(showDetails)
            Mockito.verify(repository).storeShowInDatabase(showDetails)
            assert(viewModel.state.value is ShowDetailsState.Error)
        }
    }

}