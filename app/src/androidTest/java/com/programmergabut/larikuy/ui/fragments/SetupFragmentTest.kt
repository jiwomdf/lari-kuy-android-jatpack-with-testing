package com.programmergabut.larikuy.ui.fragments

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
import com.programmergabut.larikuy.onSnackbar
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SetupFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testVisibility_allVisible(){
        launchFragmentInHiltContainer<SetupFragment> {}

        onView(withId(R.id.tvWelcome)).check(matches(isDisplayed()))
        onView(withId(R.id.tvContinue)).check(matches(isDisplayed()))
        onView(withId(R.id.etName)).check(matches(isDisplayed()))
        onView(withId(R.id.etWeight)).check(matches(isDisplayed()))
    }

    @Test
    fun clickTvContinue_showSnackBarWithFailedMessageBecauseFieldIsEmpty(){
        launchFragmentInHiltContainer<SetupFragment> {}

        onView(withId(R.id.tvContinue)).perform(click())

        val snackBarText = onSnackbar("Please enter all the fields")

        //TODO create the real snackBar view that testable
        assertThat(snackBarText).isNotNull()
    }

    @Test
    fun fillEtNameAndEtWeightThanClickTvContinue_SaveDataToSharedPrefAndNavigateToRunFragment(){

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<SetupFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.etName)).perform(replaceText("Jiwomdf"))
        onView(withId(R.id.etWeight)).perform(replaceText("55"))
        onView(withId(R.id.tvContinue)).perform(click())

        //TODO add sharedPreferences save data simulation

        verify(navController).navigate(
            SetupFragmentDirections.actionSetupFragmentToRunFragment()
        )
    }


}