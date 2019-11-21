package praneeth.com.sportsapp.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import praneeth.com.sportsapp.domain.dataModels.PlayersResponse
import praneeth.com.sportsapp.domain.service.RetrofitServiceRepository
import praneeth.com.sportsapp.domain.service.ServiceResult

/**
 * Created by Praneeth on 2019-11-18.
 */
class SearchViewModel(private val repository: RetrofitServiceRepository): ViewModel() {
    private var mResponse = MutableLiveData<PlayersResponse>()
    private var mShouldDismissProgressDialog = MutableLiveData<Boolean>()
    private var mToastString = MutableLiveData<String>()

    val response: LiveData<PlayersResponse> = mResponse
    val shouldDismissProgressDialog: LiveData<Boolean> = mShouldDismissProgressDialog
    val toastString: LiveData<String> = mToastString

    fun search(team: String) {
        mResponse.value = null
        viewModelScope.launch {
            when (val serviceResult = repository.getPlayerDetails(team)) {
                is ServiceResult.Error -> {
                    mShouldDismissProgressDialog.value = true
                    mToastString.value = serviceResult.exception.string
                }

                is ServiceResult.Success -> {
                    if (serviceResult.data.players.isNullOrEmpty()) {
                        mShouldDismissProgressDialog.value = true
                        mToastString.value= "Please search for a valid team"
                    } else {
                        mShouldDismissProgressDialog.value = true
                        mResponse.value = serviceResult.data
                    }
                }
            }
        }
    }
}
