package praneeth.com.sportsapp.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Praneeth on 2019-11-20.
 */
class TestObserver<T> : Observer<T> {
    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}
