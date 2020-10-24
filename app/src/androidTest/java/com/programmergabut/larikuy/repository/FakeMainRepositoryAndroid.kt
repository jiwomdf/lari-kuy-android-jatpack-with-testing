package com.programmergabut.larikuy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.programmergabut.larikuy.db.Run
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FakeMainRepositoryAndroid: MainRepository{

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

    private fun refreshLiveData(){
        observableRunsByDate.postValue(runs.sortedByDescending { it.timestamp })
        observableRunsByDistance.postValue(runs.sortedByDescending { it.distanceInMater })
        observableRunsByTimeInMillis.postValue(runs.sortedByDescending { it.timeInMillis })
        observableRunsByAvgSpeed.postValue(runs.sortedByDescending { it.avgSpeedInKMH })
        observableRunsByCaloriesBurned.postValue(runs.sortedByDescending { it.caloriesBurned })

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
        return observableRunsByDate
    }

    override fun getAllRunsSortedByDistance(): LiveData<List<Run>> {
        return observableRunsByDistance
    }

    override fun getAllRunsSortedByTimeInMillis(): LiveData<List<Run>> {
        return observableRunsByTimeInMillis
    }

    override fun getAllRunsSortedByAvgSpeed(): LiveData<List<Run>> {
        return observableRunsByAvgSpeed
    }

    override fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Run>> {
        return observableRunsByCaloriesBurned
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