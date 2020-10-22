package com.programmergabut.larikuy

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

object ActivityHelper {

    fun getCurrentActivity(): Activity? {
        var currentActivity: Activity? = null
        getInstrumentation().runOnMainSync {
            run {
                currentActivity = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED)
                    .elementAtOrNull(0)
            }
        }
        return currentActivity
    }
}