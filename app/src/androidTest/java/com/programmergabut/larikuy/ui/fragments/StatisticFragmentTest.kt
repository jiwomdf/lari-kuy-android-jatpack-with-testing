package com.programmergabut.larikuy.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.launchFragmentInHiltContainer
import com.programmergabut.larikuy.repository.FakeMainRepository
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import com.programmergabut.larikuy.ui.viewmodels.StatisticViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class StatisticFragmentTest {

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
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = StatisticViewModel(FakeMainRepository())

        launchFragmentInHiltContainer<StatisticFragment>(fragmentFactory = testMainFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.tvTotalDistanceInfo)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTotalDistance)).check(matches(isDisplayed()))

        onView(withId(R.id.tvTotalTimeInfo)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTotalTime)).check(matches(isDisplayed()))

        onView(withId(R.id.tvTotalCaloriesInfo)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTotalCalories)).check(matches(isDisplayed()))

        onView(withId(R.id.tvAverageSpeedInfo)).check(matches(isDisplayed()))
        onView(withId(R.id.tvAverageSpeed)).check(matches(isDisplayed()))

        onView(withId(R.id.barChart)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickOnBar_barShowCustomMarkerView(){
        //TODO how to do that ?
    }

}