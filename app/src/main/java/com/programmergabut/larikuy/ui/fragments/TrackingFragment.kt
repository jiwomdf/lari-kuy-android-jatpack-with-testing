package com.programmergabut.larikuy.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment: Fragment(R.layout.fragment_tracking) {

    private val viewModel: MainViewModel by viewModels()
}