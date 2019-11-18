package praneeth.com.sportapp.domain.service

import praneeth.com.sportapp.domain.dataModels.PlayersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("searchplayers.php")
    suspend fun getPlayerDetails(@Query("t") word: String?
    ): retrofit2.Response<PlayersResponse>
}
