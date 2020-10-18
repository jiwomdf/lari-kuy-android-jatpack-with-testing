package com.programmergabut.larikuy.repository

import com.programmergabut.larikuy.db.Run
import com.programmergabut.larikuy.db.RunDao
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    val runDao: RunDao
) : MainRepository {
    override suspend fun insertRun(run: Run) = runDao.insertRun(run)
    override suspend fun deleteRun(run: Run) = runDao.deleteRun(run)
    override fun getAllRunsSortedByDate() = runDao.getAllRunSortedByDate()
    override fun getAllRunsSortedByDistance() = runDao.getAllRunSortedByDistance()
    override fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunSortedByTimeInMillis()
    override fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunSortedByAvgSpeed()
    override fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunSortedByCaloriesBurn()
    override fun getTotalAvgSpeed() = runDao.getTotaTotalAvgSpeed()
    override fun getTotalDistance() = runDao.getTotalDistance()
    override fun getTotalCaloriesBurn() = runDao.getTotalCaloriesBurn()
    override fun getTotalTimeInMills() = runDao.getTotalTimeInMills()
}