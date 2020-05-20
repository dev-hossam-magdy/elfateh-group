package com.example.elfatehgroup.ui

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.example.elfatehgroup.R

fun ProgressBar.showOrHideProgressBar(isLoading: Boolean) {

    Log.e("ProgressBar", "showOrHideProgressBar: isLoading $isLoading")
    this.visibility =
        if (isLoading)
            View.VISIBLE
        else
            View.GONE
}


fun Activity.displayDialogMessage(@StringRes titleRecurse: Int, msg: String) {
    MaterialDialog(this)
        .show {
            title(titleRecurse)
            message(text = msg)
            positiveButton(R.string.text_ok)
        }

}

fun Activity.displayCheckNetWorkDialogMessage(@StringRes titleRecurse: Int, msg: String) {
    MaterialDialog(this)
        .show {
            title(titleRecurse)
            message(text = msg)
            positiveButton(R.string.retry)
        }

}

fun Activity.displayToastMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}