package com.ayprotech.spacex.util

import android.view.View
import androidx.fragment.app.Fragment
import com.ayprotech.spacex.R
import com.ayprotech.spacex.data.network.Resource
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            getString(R.string.no_internet),
            retry
        )
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}