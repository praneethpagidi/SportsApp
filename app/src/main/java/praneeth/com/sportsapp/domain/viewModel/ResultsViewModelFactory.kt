package praneeth.com.sportsapp.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import praneeth.com.sportsapp.domain.dataModels.PlayersResponse

@Suppress("UNCHECKED_CAST")
class ResultsViewModelFactory(private val playersResponse: PlayersResponse?): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResultsViewModel(playersResponse) as T
    }

}