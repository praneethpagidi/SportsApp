package praneeth.com.sportsapp.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import praneeth.com.sportsapp.domain.dataModels.Player
import praneeth.com.sportsapp.domain.dataModels.PlayersResponse

/**
 * Created by Praneeth on 2019-11-19.
 */
class ResultsViewModel(playersResponse: PlayersResponse?): ViewModel() {
    private var mResponse = MutableLiveData<PlayersResponse>()
    val response: LiveData<PlayersResponse> = mResponse

    init {
        if (playersResponse != null)
            mResponse.value = playersResponse
    }

    fun getPlayers(playersFromResponse: List<Player?>?): List<Player> {
        if (playersFromResponse.isNullOrEmpty())
            return emptyList()

        return playersFromResponse.filterNotNull()
    }
}
