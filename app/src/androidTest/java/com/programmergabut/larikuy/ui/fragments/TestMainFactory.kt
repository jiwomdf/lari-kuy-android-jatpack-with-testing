package com.programmergabut.larikuy.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.programmergabut.larikuy.repository.FakeMainRepository
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import javax.inject.Inject

class TestMainFactory @Inject constructor(): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            RunFragment::class.java.name -> RunFragment(
                MainViewModel(FakeMainRepository())
            )
            SettingFragment::class.java.name -> SettingFragment()
            SetupFragment::class.java.name -> SetupFragment()
            StatisticFragment::class.java.name -> StatisticFragment()
            TrackingFragment::class.java.name -> TrackingFragment()
            else -> super.instantiate(classLoader, className)
        }

    }
}