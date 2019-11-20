package praneeth.com.sportsapp.domain.service

import praneeth.com.sportsapp.domain.dataModels.PlayersResponse
import retrofit2.http.Query

/**
 * Created by Praneeth on 2019-11-18.
 */
interface RetrofitServiceRepository {
    suspend fun getPlayerDetails(@Query("t") team: String
    ): ServiceResult<PlayersResponse>
}
