package com.programmergabut.larikuy.repository

import androidx.lifecycle.LiveData
import com.programmergabut.larikuy.db.Run

interface MainRepository {
    suspend fun insertRun(run: Run)
    suspend fun deleteRun(run: Run)
    fun getAllRunsSortedByDate(): LiveData<List<Run>>
    fun getAllRunsSortedByDistance(): LiveData<List<Run>>
    fun getAllRunsSortedByTimeInMillis(): LiveData<List<Run>>
    fun getAllRunsSortedByAvgSpeed(): LiveData<List<Run>>
    fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Run>>
    fun getTotalAvgSpeed(): LiveData<Float>
    fun getTotalDistance(): LiveData<Int>
    fun getTotalCaloriesBurn(): LiveData<Int>
    fun getTotalTimeInMills(): LiveData<Long>
}