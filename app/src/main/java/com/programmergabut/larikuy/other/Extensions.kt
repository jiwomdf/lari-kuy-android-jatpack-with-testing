package com.programmergabut.larikuy.other

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(
    message: String,
    length: Int
) = Snackbar.make(this, message, length).show()