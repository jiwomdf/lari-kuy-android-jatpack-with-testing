package com.programmergabut.larikuy.ui.viewmodels

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.programmergabut.larikuy.MainCoroutineRuleAndroidTest
import com.programmergabut.larikuy.db.Run
import com.programmergabut.larikuy.getOrAwaitValueAndroidTest
import com.programmergabut.larikuy.other.SortType
import com.programmergabut.larikuy.repository.FakeMainRepositoryAndroid
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StatisticViewModelAndroidTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRuleAndroidTest()

    private lateinit var viewModel: StatisticViewModel

    @Before
    fun setup(){
        viewModel = StatisticViewModel(FakeMainRepositoryAndroid())
    }

    @Test
    fun getTotalTimeRun(){
        val dbData = viewModel.totalTimeRun.getOrAwaitValueAndroidTest()
        assertThat(dbData).isEqualTo(5L)
    }

    @Test
    fun getTotalDistance(){
        val dbData = viewModel.totalDistance.getOrAwaitValueAndroidTest()
        assertThat(dbData).isEqualTo(5)
    }

    @Test
    fun getTotalCaloriesBurned(){
        val dbData = viewModel.totalCaloriesBurned.getOrAwaitValueAndroidTest()
        assertThat(dbData).isEqualTo(5)
    }

    @Test
    fun getTotalAvgSpeed(){
        val dbData = viewModel.totalAvgSpeed.getOrAwaitValueAndroidTest()
        assertThat(dbData).isEqualTo(5F)
    }

    @Test
    fun getRunsSortedByDate(){
        val dbData = viewModel.runsSortedByDate.getOrAwaitValueAndroidTest()
        assertThat(dbData).isEmpty()
    }

}