package com.programmergabut.larikuy.repository

import com.programmergabut.larikuy.db.Run
import com.programmergabut.larikuy.db.RunDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao: RunDao
) {
    suspend fun insertRun(run: Run) = runDao.insertRun(run)
    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)
    fun getAllRunsSortedByDate() = runDao.getAllRunSortedByDate()
    fun getAllRunsSortedByDistance() = runDao.getAllRunSortedByDistance()
    fun getAllRunsSortedByTimeInMills() = runDao.getAllRunSortedByTimeInMillis()
    fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunSortedByAvgSpeed()
    fun getAllRunsSortedByCaloriesBurn() = runDao.getAllRunSortedByCaloriesBurn()
    fun getTotalAvgSpeed() = runDao.getTotaTotalAvgSpeed()
    fun getTotalDistance() = runDao.getTotalDistance()
    fun getTotalCaloriesBurn() = runDao.getTotalCaloriesBurn()
    fun getTotalTimeInMills() = runDao.getTotalTimeInMills()
}