package praneeth.com.sportsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.find
import praneeth.com.sportsapp.R
import praneeth.com.sportsapp.domain.dataModels.DetailsDataModel
import praneeth.com.sportsapp.domain.dataModels.Player
import praneeth.com.sportsapp.domain.dataModels.PlayersResponse
import praneeth.com.sportsapp.domain.viewModels.ResultsViewModel
import praneeth.com.sportsapp.ui.adapters.PlayersAdapter

/**
 * Created by Praneeth on 2019-11-18.
 */

const val dataModelParcelKey = "dataModel"
class ResultsFragment : BaseFragment() {
    override val layoutResourceId = R.layout.results_view
    override val screenTitle = "Players"

    private val viewModel by lazy {
        obtainViewModel {
            ResultsViewModel(getResponse())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewAndObserve(view)
    }

    private fun initRecyclerViewAndObserve(view: View) {
        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            if (response != null && !response.players.isNullOrEmpty()) {
                view.find<RecyclerView>(R.id.recycler_view_players).run {
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    adapter = PlayersAdapter(viewModel.getPlayers(response.players), object : PlayersAdapter.OnItemClickListener{
                        override fun onItemClicked(player: Player) {
                            navigateToNextFragment(R.id.action_resultsFragment_to_playerDetailsFragment, dataModelParcelKey , prepareDetailsDataModel(player))
                        }
                    })
                }
            }
        })
    }

    private fun getResponse(): PlayersResponse? {
        return requireArguments().getParcelable(responseParcelKey)
    }

    private fun prepareDetailsDataModel(player: Player): DetailsDataModel {
        return DetailsDataModel.Builder()
            .withDescription(if (player.strDescriptionEN.isNullOrBlank()) "" else player.strDescriptionEN)
            .withFacebook(if (player.strFacebook.isNullOrBlank()) "" else player.strFacebook)
            .withInstagram(if (player.strInstagram.isNullOrBlank()) "" else player.strInstagram)
            .build()
    }
}
