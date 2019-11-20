package praneeth.com.sportsapp.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import praneeth.com.sportsapp.domain.dataModels.DetailsDataModel

/**
 * Created by Praneeth on 2019-11-19.
 */
class DetailsViewModel(dataModel: DetailsDataModel?): ViewModel() {
    private var mDescription = MutableLiveData<String>()
    private var mFacebook = MutableLiveData<String>()
    private var mInstagram = MutableLiveData<String>()

    val description: LiveData<String> = mDescription
    val facebook: LiveData<String> = mFacebook
    val instagram: LiveData<String> = mInstagram

    init {
        mDescription.value = dataModel?.description ?: ""
        mFacebook.value = dataModel?.facebookLink ?: ""
        mInstagram.value = dataModel?.instagramLink ?: ""
    }

}
