package praneeth.com.sportapp.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.search_view.*
import org.jetbrains.anko.support.v4.toast
import praneeth.com.sportapp.R
import praneeth.com.sportapp.domain.viewModels.MyViewModel
import praneeth.com.sportapp.ui.activities.MainActivity

/**
 * Created by Praneeth on 2019-11-18.
 */

class SearchTeamFragment: BaseFragment(), View.OnClickListener {
    override val layoutResourceId: Int = R.layout.search_view
    override val screenTitle = "Search a Team"

    private lateinit var mProgressbar: ProgressDialog
    private val viewModel by lazy {
        MyViewModel()
    }

    private var keyWord: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewComponents()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewComponents() {
        et_searchAWord.hint = getString(R.string.search_a_team)
        et_searchAWord.addTextChangedListener(textWatcher())
        mProgressbar = ProgressDialog(requireActivity())
        btn_search.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val activity = requireActivity() as MainActivity
        if (activity.supportActionBar != null) {
            activity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }


    override fun onClick(v: View) {
        hideKeyboard()
        if (keyWord.isBlank()) {
            toast("Please enter a word")
            return
        }
        showProgressDialog()
        searchTeamAndAddObservers()
    }

    private fun searchTeamAndAddObservers() {
        viewModel.search(keyWord)

        viewModel.response.observe(viewLifecycleOwner,
            Observer { response ->
                if (response != null) {
                    mProgressbar.dismiss()
                    navigateToNextFragment(R.id.action_searchFragment_to_resultsFragment)
                }
            })

        viewModel.shouldDismissProgressDialog.observe(viewLifecycleOwner,
            Observer { shouldDismissProgressDialog ->
                if (shouldDismissProgressDialog) {
                    mProgressbar.dismiss()
                }
            })

        viewModel.toastString.observe(viewLifecycleOwner,
            Observer {
                toast(it)
            })
    }

    private fun textWatcher(): TextWatcher {
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