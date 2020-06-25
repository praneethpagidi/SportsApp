package praneeth.com.sportsapp.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import praneeth.com.sportsapp.domain.dataModels.PlayersResponse
import praneeth.com.sportsapp.domain.service.RetrofitServiceRepository
import praneeth.com.sportsapp.domain.service.ServiceResult
import praneeth.com.sportsapp.util.SingleLiveDataEvent

/**
 * Created by Praneeth on 2019-11-18.
 */
class SearchViewModel(private val repository: RetrofitServiceRepository): ViewModel() {
    private var mResponse = MutableLiveData<SingleLiveDataEvent<PlayersResponse>>()
    private var mShouldDismissProgressDialog = MutableLiveData<SingleLiveDataEvent<Boolean>>()
    private var mToastString = MutableLiveData<SingleLiveDataEvent<String>>()

    val response: LiveData<SingleLiveDataEvent<PlayersResponse>> = mResponse
    val shouldDismissProgressDialog: LiveData<SingleLiveDataEvent<Boolean>> = mShouldDismissProgressDialog
    val toastString: LiveData<SingleLiveDataEvent<String>> = mToastString

    fun search(team: String) {
        mResponse.value = null
        viewModelScope.launch {
            when (val serviceResult = repository.getPlayerDetails(team)) {
                is ServiceResult.Error -> {
                    mShouldDismissProgressDialog.value = SingleLiveDataEvent(true)
                    mToastString.value = SingleLiveDataEvent(serviceResult.exception.string)
                }

                is ServiceResult.Success -> {
                    mShouldDismissProgressDialog.value = SingleLiveDataEvent(true)
                    if (serviceResult.data.players.isNullOrEmpty()) {
                        mToastString.value= SingleLiveDataEvent("Please search for a valid team")
                    } else {
                        mResponse.value = SingleLiveDataEvent(serviceResult.data)
                    }
                }
            }
        }
    }
}
