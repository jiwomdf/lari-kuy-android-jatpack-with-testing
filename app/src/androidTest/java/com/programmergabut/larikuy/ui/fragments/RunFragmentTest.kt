package com.programmergabut.larikuy.ui.fragments

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.db.Run
import com.programmergabut.larikuy.getOrAwaitValue
import com.programmergabut.larikuy.launchFragmentInHiltContainer
import com.programmergabut.larikuy.repository.FakeMainRepository
import com.programmergabut.larikuy.ui.adapter.RunAdapter
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class RunFragmentTest{

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
    fun testVisibility_allVisible(){

        val testViewModel = MainViewModel(FakeMainRepository())
        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {
            viewModel = testViewModel
        }

        onView(withId(R.id.spFilter)).check(matches(isDisplayed()))
        onView(withId(R.id.tvFilterBy)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRuns)).check(matches(isDisplayed()))
        onView(withId(R.id.fabAddRun)).check(matches(isDisplayed()))
    }

    @Test
    fun testScrollRecyclerView_ScrollToEnd() {

        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {}

        onView(withId(R.id.rvRuns)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RunAdapter.RunViewHolder>(
                4,
                ViewActions.scrollTo()
            )
        )
    }

    @Test
    fun clickFloatingButton_navigateToTrackingFragment(){
        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())

        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.fabAddRun)).check(matches(isDisplayed()))
        onView(withId(R.id.fabAddRun)).perform(click())

        verify(navController).navigate(
            RunFragmentDirections.actionRunFragmentToTrackingFragment()
        )
    }

    @Test
    fun deleteLastRun_deleteComplete(){
        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())
        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)

        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel

            testViewModel.insertRun(
                Run(
                    img = bmp,
                    timestamp = 99L,
                    avgSpeedInKMH = 99F,
                    distanceInMater = 99,
                    timeInMillis = 99L,
                    caloriesBurned = 99
                )
            )
        }

//        onView(withId(R.id.rvRuns)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RunAdapter.RunViewHolder>(
//                5,
//                ViewActions.swipeLeft()
//            )
//        )

        assertThat(testViewModel.runs.getOrAwaitValue().size).isEqualTo(6)
    }

    @Test
    fun changeSpinnerToSortByDate_runsSortedByDate(){

        val expectedData = mutableListOf(5L, 4L, 3L, 2L, 1L) //this is represent the sorted descending data of timestamp
        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())

        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.spFilter)).perform(click()) //click on the spinner
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Date"))).perform(click()) //select spinner which named

        val testData = mutableListOf<Long>() //just select the timestamp from the viewModel
        testViewModel.runs.getOrAwaitValue().forEach {
            testData.add(it.timestamp)
        }

        assertThat(
            testData
        ).isEqualTo(
            expectedData
        )
    }

    @Test
    fun changeSpinnerToSortByRunningTime_runsSortedByRunningTime(){
        val expectedData = mutableListOf(5L, 4L, 3L, 2L, 1L)
        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())

        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.spFilter)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Running Time"))).perform(click())

        val testData = mutableListOf<Long>()
        testViewModel.runs.getOrAwaitValue().forEach {
            testData.add(it.timeInMillis)
        }

        assertThat(
            testData
        ).isEqualTo(
            expectedData
        )
    }

    @Test
    fun changeSpinnerToSortByDistance_runsSortedByDistance(){
        val expectedData = mutableListOf(5L, 4L, 3L, 2L, 1L)
        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())

        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.spFilter)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Distance"))).perform(click())

        val testData = mutableListOf<Long>()
        testViewModel.runs.getOrAwaitValue().forEach {
            testData.add(it.timeInMillis)
        }

        assertThat(
            testData
        ).isEqualTo(
            expectedData
        )
    }

    @Test
    fun changeSpinnerToSortByAvgSpeed_runsSortedByAvgSpeed(){
        val expectedData = mutableListOf(5L, 4L, 3L, 2L, 1L)
        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())

        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.spFilter)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Average Speed"))).perform(click())

        val testData = mutableListOf<Long>()
        testViewModel.runs.getOrAwaitValue().forEach {
            testData.add(it.timeInMillis)
        }

        assertThat(
            testData
        ).isEqualTo(
            expectedData
        )
    }

    @Test
    fun changeSpinnerToSortByCaloriesBurn_runsSortedByCaloriesBurn(){
        val expectedData = mutableListOf(5L, 4L, 3L, 2L, 1L)
        val navController = mock(NavController::class.java)
        val testViewModel = MainViewModel(FakeMainRepository())

        launchFragmentInHiltContainer<RunFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.spFilter)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Calories Burned"))).perform(click())

        val testData = mutableListOf<Long>()
        testViewModel.runs.getOrAwaitValue().forEach {
            testData.add(it.timeInMillis)
        }

        assertThat(
            testData
        ).isEqualTo(
            expectedData
        )
    }

}