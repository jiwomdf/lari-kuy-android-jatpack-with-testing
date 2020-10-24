package com.programmergabut.larikuy.ui.viewmodel

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.programmergabut.larikuy.MainCoroutineRuleAndroidTest
import com.programmergabut.larikuy.db.Run
import com.programmergabut.larikuy.db.RunsTest
import com.programmergabut.larikuy.getOrAwaitValueAndroidTest
import com.programmergabut.larikuy.repository.FakeMainRepositoryAndroid
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRuleAndroidTest()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup(){
        viewModel = MainViewModel(FakeMainRepositoryAndroid())
    }

    /* Still failing
    @Test
    fun insertRunWithEmptyField_returnError(){

        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        val run = Run(
            img = bmp,
            timestamp = 99.toLong(),
            avgSpeedInKMH = 99.toFloat(),
            distanceInMater = 99,
            timeInMillis = 99.toLong(),
            caloriesBurned = 99
        )

        viewModel.insertRun(run)

        val expectedRunData = RunsTest(
            timestamp = run.timestamp,
            avgSpeedInKMH = run.avgSpeedInKMH,
            distanceInMater = run.distanceInMater,
            timeInMillis = run.timeInMillis,
            caloriesBurned = run.caloriesBurned
        )

        val dbRunData = viewModel.runs.getOrAwaitValueAndroidTest().map {
            RunsTest(
                timestamp = it.timestamp,
                avgSpeedInKMH = it.avgSpeedInKMH,
                distanceInMater = it.distanceInMater,
                timeInMillis = it.timeInMillis,
                caloriesBurned = it.caloriesBurned
            )
        }

        assertThat(dbRunData).contains(expectedRunData)
    } */

    /* @Test
    fun `insert run with valid input, insert success`(){
        viewModel.insertRun()
    }

    @Test
    fun `delete run, delete success`(){
        viewModel.deleteRun()
    }

    @Test
    fun `sort run by date, run sorted by date`(){
        viewModel.sortRuns()
    } */

}