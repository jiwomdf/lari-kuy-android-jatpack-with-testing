package com.programmergabut.larikuy.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import com.programmergabut.larikuy.ui.viewmodels.StatisticViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment: Fragment(R.layout.fragment_statistic) {

    private val viewModel: StatisticViewModel by viewModels()
}