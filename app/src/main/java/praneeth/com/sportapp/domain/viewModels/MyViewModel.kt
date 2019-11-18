package praneeth.com.sportapp.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import praneeth.com.sportapp.domain.dataModels.PlayersResponse
import praneeth.com.sportapp.domain.service.RetrofitProvider
import praneeth.com.sportapp.domain.service.RetrofitService
import praneeth.com.sportapp.domain.service.RetrofitServiceRepositoryImpl
import praneeth.com.sportapp.domain.service.ServiceResult

/**
 * Created by Praneeth on 2019-11-18.
 */
class MyViewModel: ViewModel() {
    private var mResponse = MutableLiveData<PlayersResponse>()
    private var mShouldDismissProgressDialog = MutableLiveData<Boolean>()
    private var mToastString = MutableLiveData<String>()

    val response: LiveData<PlayersResponse> = mResponse
    val shouldDismissProgressDialog: LiveData<Boolean> = mShouldDismissProgressDialog
    val toastString: LiveData<String> = mToastString

    private val mService: RetrofitService by lazy { RetrofitProvider.create() }


    fun search(team: String) {
        mResponse.value = null
        val serviceImpl = RetrofitServiceRepositoryImpl(mService)

        viewModelScope.launch {
            val serviceResult = serviceImpl.getPlayerDetails(team)
            when (serviceResult) {
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
