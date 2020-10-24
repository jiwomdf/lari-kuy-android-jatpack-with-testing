package com.programmergabut.larikuy.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.programmergabut.larikuy.repository.MainRepository

class StatisticViewModel @ViewModelInject constructor(val mainRepository: MainRepository): ViewModel() {

    val totalTimeRun = mainRepository.getTotalTimeInMills()
    val totalDistance = mainRepository.getTotalDistance()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurn()
    val totalAvgSpeed = mainRepository.getTotalAvgSpeed()

    val runsSortedByDate = mainRepository.getAllRunsSortedByDate()

}