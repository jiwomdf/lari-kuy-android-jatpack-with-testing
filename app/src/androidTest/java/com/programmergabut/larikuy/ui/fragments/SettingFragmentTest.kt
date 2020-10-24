package com.programmergabut.larikuy.ui.fragments

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.launchFragmentInHiltContainer
import com.programmergabut.larikuy.other.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SettingFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testMainFactory: TestMainFactory

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testVisibility_allVisible(){
        launchFragmentInHiltContainer<SettingFragment> {}

        onView(withId(R.id.etName)).check(matches(isDisplayed()))
        onView(withId(R.id.etWeight)).check(matches(isDisplayed()))
    }

    @Test
    fun insertEmptyData_failedAndShowSnackBar(){
        val expectedName = "jiwo"
        val expectedWeight = 55f

        launchFragmentInHiltContainer<SettingFragment> {}

        onView(withId(R.id.etName)).perform(replaceText(""))
        onView(withId(R.id.etWeight)).perform(replaceText(""))
        onView(withId(R.id.btnApplyChanges)).perform(click())

        val sharedPrefName = sharedPref.getString(Constants.KEY_NAME, "") ?: ""
        val sharedPrefWeight = sharedPref.getFloat(Constants.KEY_WEIGHT, 0f)

        assertThat(sharedPrefName).isEqualTo(expectedName)
        assertThat(sharedPrefWeight).isEqualTo(expectedWeight)

        //TODO test the snackBar message
    }

    @Test
    fun insertValidData_sharedPrefUpdatedAndTitleBarNameChanged(){
        val expectedName = "jiwo"
        val expectedWeight = 55f
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<SettingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.etName)).perform(replaceText(expectedName))
        onView(withId(R.id.etWeight)).perform(replaceText(expectedWeight.toString()))
        onView(withId(R.id.btnApplyChanges)).perform(click())

        val sharedPrefName = sharedPref.getString(Constants.KEY_NAME, "") ?: ""
        val sharedPrefWeight = sharedPref.getFloat(Constants.KEY_WEIGHT, 0f)

        // assert the sharedPref
        assertThat(sharedPrefName).isEqualTo(expectedName)
        assertThat(sharedPrefWeight).isEqualTo(expectedWeight)

        //TODO we can't test the toolbar text because now we are using HiltTestActivity not the MainActivity
        /*
        val activity = ActivityHelper.getCurrentActivity()
        val toolBarTxt = ActivityHelper.getCurrentActivity()?.tvToolbarTitle?.text.toString()

        //assert toolbar text
        assertThat(toolBarTxt).isEqualTo("Let's Go $expectedName") */

        //TODO test the snackBar message
    }

}