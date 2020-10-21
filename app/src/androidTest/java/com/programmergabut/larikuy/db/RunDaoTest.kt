package com.programmergabut.larikuy.db

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.programmergabut.larikuy.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class RunDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExperimentalCoroutinesApi = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: RunningDatabase

    private lateinit var runDao: RunDao

    @Before
    fun setup(){
        hiltRule.inject()
        runDao = database.runDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertRun() = runBlocking {
        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        val run = Run(
            img = bmp,
            timestamp = 99L,
            avgSpeedInKMH = 99F,
            distanceInMater = 99,
            timeInMillis = 99L,
            caloriesBurned = 99
        )

        runDao.insertRun(run)
        val runsByDate = runDao.getAllRunSortedByDate().getOrAwaitValue()

        //Because the bitmap value is always different so we need to exclude it in the comparison
        val runsTestByDate = runsByDate.map { RunsTest(
            it.timestamp,
            it.avgSpeedInKMH,
            it.distanceInMater,
            it.timeInMillis,
            it.caloriesBurned
        ) }

        val insertedRunTest = RunsTest(
            timestamp = run.timestamp,
            avgSpeedInKMH = run.avgSpeedInKMH,
            distanceInMater = run.distanceInMater,
            timeInMillis = run.timeInMillis,
            caloriesBurned = run.caloriesBurned
        )

        assertThat(runsTestByDate).contains(insertedRunTest)
    }

    @Test
    fun deleteRun() = runBlockingTest {
        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        val run = Run(
            img = bmp,
            timestamp = 99L,
            avgSpeedInKMH = 99F,
            distanceInMater = 99,
            timeInMillis = 99L,
            caloriesBurned = 99
        )

        //TODO Still failing
        runDao.insertRun(run)
        runDao.deleteRun(run)

        val runsByDate = runDao.getAllRunSortedByDate().getOrAwaitValue()

        //Because the bitmap value is always different so we need to exclude it in the comparison
        val runsTestByDate = runsByDate.map { RunsTest(
            it.timestamp,
            it.avgSpeedInKMH,
            it.distanceInMater,
            it.timeInMillis,
            it.caloriesBurned
        ) }

        val insertedRunTest = RunsTest(
            timestamp = run.timestamp,
            avgSpeedInKMH = run.avgSpeedInKMH,
            distanceInMater = run.distanceInMater,
            timeInMillis = run.timeInMillis,
            caloriesBurned = run.caloriesBurned
        )

        assertThat(runsTestByDate).doesNotContain(insertedRunTest)
    }

    @Test
    fun getTotalAvgSpeed() = runBlockingTest {

        val expectedData = (1F + 2F + 3F + 4F + 5F) / 5F
        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)

        for(i in 1 until 6){
            val run = Run(
                img = bmp,
                timestamp = i.toLong(),
                avgSpeedInKMH = i.toFloat(),
                distanceInMater = i,
                timeInMillis = i.toLong(),
                caloriesBurned = i
            )
            runDao.insertRun(run)
        }

        val totalTotalAvgSpeed = runDao.getTotaTotalAvgSpeed().getOrAwaitValue()
        assertThat(totalTotalAvgSpeed).isEqualTo(expectedData)
    }

    @Test
    fun getTotalDistance() = runBlockingTest {

        val expectedData = 15
        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)

        for(i in 1 until 6){
            val run = Run(
                img = bmp,
                timestamp = i.toLong(),
                avgSpeedInKMH = i.toFloat(),
                distanceInMater = i,
                timeInMillis = i.toLong(),
                caloriesBurned = i
            )
            runDao.insertRun(run)
        }

        val totalDistance = runDao.getTotalDistance().getOrAwaitValue()
        assertThat(totalDistance).isEqualTo(expectedData)
    }

    @Test
    fun getTotalCaloriesBurn() = runBlockingTest {

        val expectedData = 15
        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)

        for(i in 1 until 6){
            val run = Run(
                img = bmp,
                timestamp = i.toLong(),
                avgSpeedInKMH = i.toFloat(),
                distanceInMater = i,
                timeInMillis = i.toLong(),
                caloriesBurned = i
            )
            runDao.insertRun(run)
        }

        val totalCaloriesBurn = runDao.getTotalCaloriesBurn().getOrAwaitValue()
        assertThat(totalCaloriesBurn).isEqualTo(expectedData)
    }

    @Test
    fun getTotalTimeInMills() = runBlockingTest {

        val expectedData = 15
        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)

        for(i in 1 until 6){
            val run = Run(
                img = bmp,
                timestamp = i.toLong(),
                avgSpeedInKMH = i.toFloat(),
                distanceInMater = i,
                timeInMillis = i.toLong(),
                caloriesBurned = i
            )
            runDao.insertRun(run)
        }

        val totalTimeInMills = runDao.getTotalTimeInMills().getOrAwaitValue()
        assertThat(totalTimeInMills).isEqualTo(expectedData)
    }



}