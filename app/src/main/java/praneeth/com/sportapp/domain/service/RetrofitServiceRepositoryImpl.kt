package praneeth.com.sportapp.domain.service

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import praneeth.com.sportapp.domain.dataModels.PlayersResponse

/**
 * Created by Praneeth on 2019-11-18.
 */

class RetrofitServiceRepositoryImpl(
    private val service: RetrofitService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RetrofitServiceRepository {
    override suspend fun getPlayerDetails(team: String
    ): ServiceResult<PlayersResponse> {
        val result = withContext(dispatcher) {
            RetrofitCallbkHandler.processCall {
                service.getPlayerDetails(team)
            }
        }
        return when (result) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
        }
    }
}