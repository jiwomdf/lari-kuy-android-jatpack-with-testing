package com.programmergabut.larikuy.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.programmergabut.larikuy.repository.MainRepositoryImpl

class StatisticViewModel @ViewModelInject constructor(mainRepositoryImpl: MainRepositoryImpl): ViewModel() {

    val totalTimeRun = mainRepositoryImpl.getTotalTimeInMills()
    val totalDistance = mainRepositoryImpl.getTotalDistance()
    val totalCaloriesBurned = mainRepositoryImpl.getTotalCaloriesBurn()
    val totalAvgSpeed = mainRepositoryImpl.getTotalAvgSpeed()

    val runsSortedByDate = mainRepositoryImpl.getAllRunsSortedByDate()

}