package praneeth.com.sportsapp.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import praneeth.com.sportsapp.domain.service.RetrofitServiceRepository

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(private val repository: RetrofitServiceRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}