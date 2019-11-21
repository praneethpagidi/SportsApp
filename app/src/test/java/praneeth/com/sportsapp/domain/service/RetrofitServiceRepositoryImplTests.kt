package praneeth.com.sportsapp.domain.service

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import praneeth.com.sportsapp.domain.dataModels.PlayersResponse
import retrofit2.Response
import org.hamcrest.CoreMatchers.`is` as match

/**
 * Created by Praneeth on 2019-11-21.
 */
@ExperimentalCoroutinesApi
class RetrofitServiceRepositoryImplTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mRetrofitService: RetrofitService
    private lateinit var mRetrofitServiceRepositoryImpl: RetrofitServiceRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mRetrofitServiceRepositoryImpl = spyk(RetrofitServiceRepositoryImpl(mRetrofitService, Dispatchers.Unconfined))
    }

    @Test
    fun `getPlayerDetails when invoked if there is an error should return the result itself`() {
        runBlockingTest {
            val mockResponse: Response<PlayersResponse> = mockk()
            every { mockResponse.isSuccessful } returns false
            every { mockResponse.body() } returns null
            every { mockResponse.code() } returns 123
            every { mockResponse.message() } returns "BAD RESPONE"
            mockkStatic(Log::class)
            every { Log.d(any(), any()) } returns 0
            coEvery { mRetrofitService.getPlayerDetails(any()) } returns mockResponse

            val result = mRetrofitServiceRepositoryImpl.getPlayerDetails(anyString())

            coVerify { mRetrofitService.getPlayerDetails(any()) }

            val resultError =result as ServiceResult.Error
            val expectedErrorString = "Sorry, there was service issue, please try again"
            val expectedServiceException = ServiceResult.ServiceException("123", expectedErrorString)
            assertThat(resultError.exception, match(expectedServiceException))
        }
    }

    @Test
    fun `getPlayerDetails when invoked if there is no error should return the result success`() {
        runBlockingTest {
            val mockResponse: Response<PlayersResponse> = mockk()
            val playersResponse: PlayersResponse = mockk()
            every { mockResponse.isSuccessful } returns true
            every { mockResponse.body() } returns playersResponse
            coEvery { mRetrofitService.getPlayerDetails(any()) } returns mockResponse

            val result = mRetrofitServiceRepositoryImpl.getPlayerDetails(anyString())

            coVerify { mRetrofitService.getPlayerDetails(any()) }

            val resultSuccess = result as ServiceResult.Success
            assertThat(resultSuccess.data, match(playersResponse))
        }
    }
}
