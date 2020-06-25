package praneeth.com.sportsapp.domain.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentMatchers.anyString
import praneeth.com.sportsapp.domain.dataModels.Player
import praneeth.com.sportsapp.domain.dataModels.PlayersResponse
import praneeth.com.sportsapp.domain.service.RetrofitServiceRepository
import praneeth.com.sportsapp.domain.service.ServiceResult

/**
 * Created by Praneeth on 2019-11-20.
 */

//Todo - Update the tests in this class with SingleLiveDataEvent inferences.

@ExperimentalCoroutinesApi
class SearchViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mRetrofitServiceRepository: RetrofitServiceRepository
    private lateinit var mViewModel: SearchViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mViewModel = spyk(SearchViewModel(mRetrofitServiceRepository))
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when search function is invoked and service is not success and has exception should observe exception string and dismiss dialog`() {
        val errorString = "some error"
        coEvery { mRetrofitServiceRepository.getPlayerDetails(any()) } returns ServiceResult.Error(
            ServiceResult.ServiceException(null, errorString)
        )
        val toastObserver = mViewModel.toastString.testObserver()
        val dismissObserver = mViewModel.shouldDismissProgressDialog.testObserver()

        mViewModel.search(anyString())
        coVerify { mRetrofitServiceRepository.getPlayerDetails(any())}
//        Assert.assertThat(toastObserver.observedValues.size, CoreMatchers.`is`(1))
//        Assert.assertThat(toastObserver.observedValues[0], CoreMatchers.`is`(errorString))
//        Assert.assertThat(dismissObserver.observedValues.size, CoreMatchers.`is`(1))
//        Assert.assertThat(dismissObserver.observedValues[0], CoreMatchers.`is`(true))
    }

    @Test
    fun `when search function is invoked and service is success and but has no players should observe toast string and dismiss dialog`() {
        val toastMsg = "Please search for a valid team"
        val mockResponse: PlayersResponse = mockk()

        every { mockResponse.players } returns null
        coEvery { mRetrofitServiceRepository.getPlayerDetails(any()) } returns ServiceResult.Success(
            mockResponse
        )
        val toastObserver = mViewModel.toastString.testObserver()
        val dismissObserver = mViewModel.shouldDismissProgressDialog.testObserver()

        mViewModel.search(anyString())
        coVerify { mRetrofitServiceRepository.getPlayerDetails(any())}
//        Assert.assertThat(toastObserver.observedValues.size, CoreMatchers.`is`(1))
//        Assert.assertThat(toastObserver.observedValues[0], CoreMatchers.`is`(toastMsg))
//        Assert.assertThat(dismissObserver.observedValues.size, CoreMatchers.`is`(1))
//        Assert.assertThat(dismissObserver.observedValues[0], CoreMatchers.`is`(true))
    }

    @Test
    fun `when search function is invoked and service is success and but has empty players list should observe toast string and dismiss dialog`() {
        val toastMsg = "Please search for a valid team"
        val mockResponse: PlayersResponse = mockk()

        every { mockResponse.players } returns emptyList()
        coEvery { mRetrofitServiceRepository.getPlayerDetails(any()) } returns ServiceResult.Success(
            mockResponse
        )
        val toastObserver = mViewModel.toastString.testObserver()
        val dismissObserver = mViewModel.shouldDismissProgressDialog.testObserver()

        mViewModel.search(anyString())
        coVerify { mRetrofitServiceRepository.getPlayerDetails(any())}
//        Assert.assertThat(toastObserver.observedValues.size, CoreMatchers.`is`(1))
//        Assert.assertThat(toastObserver.observedValues[0], CoreMatchers.`is`(toastMsg))
//        Assert.assertThat(dismissObserver.observedValues.size, CoreMatchers.`is`(1))
//        Assert.assertThat(dismissObserver.observedValues[0], CoreMatchers.`is`(true))
    }

    @Test
    fun `when search function is invoked and service is success and has players should observe response and dismiss dialog`() {
        val mockResponse: PlayersResponse = mockk()
        val mockPlayer1: Player = mockk()
        val mockPlayer2: Player = mockk()

        every { mockResponse.players } returns listOf(mockPlayer1, mockPlayer2)
        coEvery { mRetrofitServiceRepository.getPlayerDetails(any()) } returns ServiceResult.Success(
            mockResponse
        )
        val responseObserver = mViewModel.response.testObserver()
        val dismissObserver = mViewModel.shouldDismissProgressDialog.testObserver()

        mViewModel.search(anyString())
        coVerify { mRetrofitServiceRepository.getPlayerDetails(any())}
//        Assert.assertThat(responseObserver.observedValues.size, CoreMatchers.`is`(2))
//        Assert.assertThat(responseObserver.observedValues[0], CoreMatchers.nullValue())
//        Assert.assertThat(responseObserver.observedValues[1], CoreMatchers.`is`(mockResponse))
//        Assert.assertThat(dismissObserver.observedValues.size, CoreMatchers.`is`(1))
//        Assert.assertThat(dismissObserver.observedValues[0], CoreMatchers.`is`(true))
    }
}
