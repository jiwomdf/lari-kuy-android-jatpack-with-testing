package com.programmergabut.larikuy.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.programmergabut.larikuy.db.Run
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class FakeMainRepository: MainRepository{

    private val runs = mutableListOf<Run>()
    private val observableRunsByDate = MutableLiveData<List<Run>>(runs)
    private val observableRunsByDistance = MutableLiveData<List<Run>>(runs)
    private val observableRunsByTimeInMillis = MutableLiveData<List<Run>>(runs)
    private val observableRunsByAvgSpeed = MutableLiveData<List<Run>>(runs)
    private val observableRunsByCaloriesBurned = MutableLiveData<List<Run>>(runs)

    private val observableTotalAvgSpeed = MutableLiveData(5f)
    private val observableTotalDistance = MutableLiveData(5)
    private val observableTotalCaloriesBurn = MutableLiveData(5)
    private val observableTotalTimeInMills = MutableLiveData(5L)

    init {
        runBlockingTest {
            val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
            for (i in 1 until 6) {
                insertRun(
                    Run(
                        img = bmp,
                        timestamp = i.toLong(),
                        avgSpeedInKMH = i.toFloat(),
                        distanceInMater = i,
                        timeInMillis = i.toLong(),
                        caloriesBurned = i
                    )
                )
            }
        }
    }

    private fun refreshLiveData(){
        observableRunsByDate.postValue(runs)
        observableRunsByDistance.postValue(runs)
        observableRunsByTimeInMillis.postValue(runs)
        observableRunsByAvgSpeed.postValue(runs)
        observableRunsByCaloriesBurned.postValue(runs)
    }

    override suspend fun insertRun(run: Run) {
        runs.add(run)
        refreshLiveData()
    }

    override suspend fun deleteRun(run: Run) {
        runs.remove(run)
        refreshLiveData()
    }

    override fun getAllRunsSortedByDate(): LiveData<List<Run>> {
        val data = runs.sortedByDescending { it.timestamp }

        return MutableLiveData(data)
    }

    override fun getAllRunsSortedByDistance(): LiveData<List<Run>> {
        val data = runs.sortedByDescending { it.distanceInMater }

        return MutableLiveData(data)
    }

    override fun getAllRunsSortedByTimeInMillis(): LiveData<List<Run>> {
        val data = runs.sortedByDescending { it.timeInMillis }

        return MutableLiveData(data)
    }

    override fun getAllRunsSortedByAvgSpeed(): LiveData<List<Run>> {
        val data = runs.sortedByDescending { it.avgSpeedInKMH }

        return MutableLiveData(data)
    }

    override fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Run>> {
        val data = runs.sortedByDescending { it.caloriesBurned }

        return MutableLiveData(data)
    }

    override fun getTotalAvgSpeed(): LiveData<Float> {
        return observableTotalAvgSpeed
    }

    override fun getTotalDistance(): LiveData<Int> {
        return observableTotalDistance
    }

    override fun getTotalCaloriesBurn(): LiveData<Int> {
        return observableTotalCaloriesBurn
    }

    override fun getTotalTimeInMills(): LiveData<Long> {
        return observableTotalTimeInMills
    }


}