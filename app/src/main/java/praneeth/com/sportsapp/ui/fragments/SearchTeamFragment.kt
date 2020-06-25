package praneeth.com.sportsapp.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.search_view.*
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import praneeth.com.sportsapp.R
import praneeth.com.sportsapp.domain.dataModels.PlayersResponse
import praneeth.com.sportsapp.domain.service.RetrofitProvider
import praneeth.com.sportsapp.domain.service.RetrofitService
import praneeth.com.sportsapp.domain.service.RetrofitServiceRepositoryImpl
import praneeth.com.sportsapp.domain.viewModel.SearchViewModel
import praneeth.com.sportsapp.domain.viewModel.SearchViewModelFactory
import praneeth.com.sportsapp.util.SingleLiveDataEvent

/**
 * Created by Praneeth on 2019-11-18.
 */
const val responseParcelKey = "response"
class SearchTeamFragment: BaseFragment(), View.OnClickListener {
    override val layoutResourceId = R.layout.search_view
    override val screenTitle = "Search a Team"

    private lateinit var mProgressbar: ProgressDialog
    private val mService: RetrofitService by lazy { RetrofitProvider.create() }

    private val viewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory(RetrofitServiceRepositoryImpl(mService))
    }

    private val responseObserver = Observer<SingleLiveDataEvent<PlayersResponse>> { responseEvent ->
        responseEvent?.getContentIfNotHandled()?.let {
            mProgressbar.dismiss()
            navigateToNextFragment(R.id.action_searchFragment_to_resultsFragment,"response" , it)
        }
    }

    private val progressBarObserver = Observer<SingleLiveDataEvent<Boolean>> { progressDialogEvent ->
        progressDialogEvent?.getContentIfNotHandled()?.let {
            if (it) {
                mProgressbar.dismiss()
            }
        }
    }

    private val toastStringObserver = Observer<SingleLiveDataEvent<String>> { toastStringEvent ->
        toastStringEvent?.getContentIfNotHandled()?.let {
            toast(it)
        }
    }

    private var keyWord: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewComponents()
        super.onViewCreated(view, savedInstanceState)

        viewModel.response.observe(viewLifecycleOwner, responseObserver)
        viewModel.shouldDismissProgressDialog.observe(viewLifecycleOwner, progressBarObserver)
        viewModel.toastString.observe(viewLifecycleOwner, toastStringObserver)

    }

    private fun initViewComponents() {
        find<TextInputEditText>(R.id.et_searchAWord).hint = getString(R.string.search_a_team)
        find<TextInputEditText>(R.id.et_searchAWord).addTextChangedListener(getTextWatcher())
        mProgressbar = ProgressDialog(requireActivity())
        mProgressbar.setTitle("Searching...")
        find<MaterialButton>(R.id.btn_search).backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.customDarkGreen)
        find<MaterialButton>(R.id.btn_search).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        hideKeyboard()
        if (keyWord.isBlank()) {
            toast("Please enter a word")
            return
        }
        showProgressDialog()
        viewModel.search(keyWord)
    }

    private fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //no op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //no op
            }

            override fun afterTextChanged(s: Editable?) {
                keyWord = if (!s.isNullOrEmpty() && !et_searchAWord.text.isNullOrEmpty()) {
                    s.toString().trim()
                } else {
                    ""
                }
            }
        }
    }

    private fun showProgressDialog() {
        mProgressbar.show()
        mProgressbar.setCancelable(false)
    }
}
