package com.example.gitbuddy.utils

import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.Group

object ConnectionUtils {
    fun showLoading(progressBar: ProgressBar, isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    fun showError(errorGroup: Group, isError: Boolean) {
        if (isError) {
            errorGroup.visibility = View.VISIBLE
        } else {
            errorGroup.visibility = View.GONE
        }
    }
}