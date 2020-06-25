package com.programmergabut.larikuy.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.programmergabut.larikuy.repository.MainRepository

class MainViewModel @ViewModelInject constructor(val mainRepository: MainRepository): ViewModel() {



}