package praneeth.com.sportsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import org.jetbrains.anko.find
import praneeth.com.sportsapp.R
import praneeth.com.sportsapp.domain.dataModels.DetailsDataModel
import praneeth.com.sportsapp.domain.viewModels.DetailsViewModel
import praneeth.com.sportsapp.util.bindOrHideWhenNull
import praneeth.com.sportsapp.util.show

/**
 * Created by Praneeth on 2019-11-19.
 */
class PlayerDetailsFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.player_details_view
    override val screenTitle: String = "Player Details"

    private val viewModel by lazy {
        obtainViewModel {
            DetailsViewModel(getDetailsDataModel())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.description.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrBlank()) {
                view.find<AppCompatTextView>(R.id.text_about).show(false)
            }
            view.find<AppCompatTextView>(R.id.text_about).text = requireContext().getString(R.string.text_about)
            view.find<AppCompatTextView>(R.id.text_description).bindOrHideWhenNull(it)
        })

        viewModel.facebook.observe(viewLifecycleOwner, Observer {
            if(it.isNullOrBlank()) {
                view.find<AppCompatTextView>(R.id.text_link1).show(false)
            }
            view.find<AppCompatTextView>(R.id.text_link1).text = requireContext().getString(R.string.text_facebook)
            view.find<AppCompatTextView>(R.id.text_facebook).bindOrHideWhenNull(it)
        })

        viewModel.instagram.observe(viewLifecycleOwner, Observer {
            if(it.isNullOrBlank()) {
                view.find<AppCompatTextView>(R.id.text_link2).show(false)
            }
            view.find<AppCompatTextView>(R.id.text_link2).text = requireContext().getString(R.string.text_instagram)
            view.find<AppCompatTextView>(R.id.text_instagram).bindOrHideWhenNull(it)
        })
    }

    private fun getDetailsDataModel(): DetailsDataModel? {
        val dataModel: DetailsDataModel? = requireArguments().getParcelable(dataModelParcelKey)
        if (dataModel != null)
            return dataModel
        return null
    }
}
