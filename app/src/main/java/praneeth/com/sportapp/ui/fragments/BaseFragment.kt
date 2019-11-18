package praneeth.com.sportapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

/**
 * Created by Praneeth on 2019-11-18.
 */
abstract class BaseFragment: Fragment() {
    /**
     * The Id of the resource file to be inflated in the fragment.
     */
    protected abstract val layoutResourceId: Int

    /**
     * Screen title used for the fragment
     */
    protected abstract val screenTitle: String

    /**
     * Helper method that overrides onCreateView to automatically inflate the layout provided within layoutResourceId
     */
    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().title = screenTitle
        return inflater.inflate(layoutResourceId, container, false)
    }

    /**
     * Fragment extension function for obtaining an [instance] of the view model through ViewModelProviders
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified V : ViewModel> Fragment.obtainViewModel(crossinline instance: () -> V): V {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return instance() as T
            }
        }
        return ViewModelProviders.of(this, factory).get(V::class.java)
    }

    /**
     * Hide the soft keyboard
     */
    protected fun hideKeyboard() {
        requireActivity().currentFocus?.let {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * Helper method to navigate to NextFragment
     */
    protected fun navigateToNextFragment(actionID:Int) {
        findNavController().navigate(actionID)
    }
}