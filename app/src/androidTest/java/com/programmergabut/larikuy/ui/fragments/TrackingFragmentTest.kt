package com.programmergabut.larikuy.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.launchFragmentInHiltContainer
import com.programmergabut.larikuy.repository.FakeMainRepository
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class TrackingFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testMainFactory: TestMainFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testVisibility_allVisibleExceptBtnFinishRun(){

        launchFragmentInHiltContainer<TrackingFragment>(fragmentFactory = testMainFactory) {}

        onView(withId(R.id.mapView)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTimer)).check(matches(isDisplayed()))
        onView(withId(R.id.btnToggleRun)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFinishRun)).check(matches(withEffectiveVisibility(Visibility.GONE)))

    }

    @Test
    fun testStartAndStopRun_runStateComponentChangedCorrectly(){

        launchFragmentInHiltContainer<TrackingFragment>(fragmentFactory = testMainFactory) {}

        //Start Run
        //TODO check if the map is zooming every 5 second
        onView(withId(R.id.tvTimer)).check(matches(withText("00:00:00:00")))
        onView(withId(R.id.btnToggleRun)).perform(click())
        onView(withId(R.id.btnToggleRun)).check(matches(withText("Stop")))
        onView(withId(R.id.btnFinishRun)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tvTimer)).check(matches(not(withText("00:00:00:00"))))

        //Pause Run
        onView(withId(R.id.btnToggleRun)).perform(click())
        onView(withId(R.id.btnToggleRun)).check(matches(withText("Start")))
        onView(withId(R.id.btnFinishRun)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testFinishRun_runSavedToDatabase(){

        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())
        launchFragmentInHiltContainer<TrackingFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.btnToggleRun)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.btnToggleRun)).perform(click())
        onView(withId(R.id.btnFinishRun)).perform(click())

        //TODO failing because of the snackBar didn't get any references
        //TODO how to simulate database insertion if we use the real db in this test (?)
    }

    @Test
    fun testCancelRun_runNotSavedAndNavigateToRunFragment(){
        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())
        launchFragmentInHiltContainer<TrackingFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.btnToggleRun)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.btnToggleRun)).perform(click())

        //TODO x button on toolbar cannot be click because we use the HilTestActivity
    }

    @Test
    fun testBackPressWhenTrackingRun_navigateToRunFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<TrackingFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.btnToggleRun)).perform(click())

        Thread.sleep(5000)

        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun testBackPressWhenPauseRun_showDialog(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<TrackingFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.btnToggleRun)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.btnToggleRun)).perform(click())

        pressBack()

        //TODO show dialog, but how?
    }


}