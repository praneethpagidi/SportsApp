package praneeth.com.sportsapp.util

import android.view.View
import android.widget.TextView

/**
 * Created by Praneeth on 2019-11-19.
 */

fun View.show(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun TextView.bindOrHideWhenNull(text: String?) {
    this.text = text ?: ""
    this.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
}
