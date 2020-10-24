package com.programmergabut.larikuy.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.programmergabut.larikuy.MainCoroutineRuleTest
import com.programmergabut.larikuy.getOrAwaitValueTest
import com.programmergabut.larikuy.repository.FakeMainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/* @ExperimentalCoroutinesApi
class StatisticViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRuleTest()

    private lateinit var viewModel: StatisticViewModel

    @Before
    fun setup(){
        viewModel = StatisticViewModel(FakeMainRepository())
    }

    @Test
    fun getTotalTimeRun(){
        val dbData = viewModel.totalTimeRun.getOrAwaitValueTest()
        assertThat(dbData).isEqualTo(5L)
    }

    @Test
    fun getTotalDistance(){
        val dbData = viewModel.totalDistance.getOrAwaitValueTest()
        assertThat(dbData).isEqualTo(5)
    }

    @Test
    fun getTotalCaloriesBurned(){
        val dbData = viewModel.totalCaloriesBurned.getOrAwaitValueTest()
        assertThat(dbData).isEqualTo(5)
    }

    @Test
    fun getTotalAvgSpeed(){
        val dbData = viewModel.totalAvgSpeed.getOrAwaitValueTest()
        assertThat(dbData).isEqualTo(5F)
    }

    @Test
    fun getRunsSortedByDate(){
        val dbData = viewModel.runsSortedByDate.getOrAwaitValueTest()
        assertThat(dbData).isEmpty()
    }

} */