package com.example.elfatehgroup.ui

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.example.elfatehgroup.R

public fun ProgressBar.showOrHideProgressBar(isLoadind: Boolean) {
    this.visibility = if (isLoadind)
        View.VISIBLE
    else
        View.GONE
}


fun Activity.displayDialogMessage(@StringRes titleResurse: Int, msg: String) {
    MaterialDialog(this)
        .show {
            title(titleResurse)
            message(text = msg)
            positiveButton(R.string.text_ok)
        }

}

fun Activity.displayToastMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}