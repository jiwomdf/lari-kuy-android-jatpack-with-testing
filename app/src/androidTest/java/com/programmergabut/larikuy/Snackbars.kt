package com.programmergabut.larikuy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf

fun onSnackbar(withText: String): ViewInteraction {
    return onView(
        CoreMatchers.allOf(
            ViewMatchers.withId(com.google.android.material.R.id.snackbar_text),
            ViewMatchers.withText(withText)
        )
    )
}

fun onSnackbarButton(withText: String): ViewInteraction {
    return onView(
        allOf(
            ViewMatchers.withId(com.google.android.material.R.id.snackbar_action),
            ViewMatchers.withText(withText)
        )
    )
}