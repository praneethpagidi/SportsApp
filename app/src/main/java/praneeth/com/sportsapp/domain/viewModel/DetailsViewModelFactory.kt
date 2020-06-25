package praneeth.com.sportsapp.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import praneeth.com.sportsapp.domain.dataModels.DetailsDataModel

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val dataModel: DetailsDataModel?): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(dataModel) as T
    }

}