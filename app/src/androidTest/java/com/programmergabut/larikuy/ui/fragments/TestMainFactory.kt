package com.programmergabut.larikuy.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.programmergabut.larikuy.repository.FakeMainRepositoryAndroid
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import com.programmergabut.larikuy.ui.viewmodels.StatisticViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class TestMainFactory @Inject constructor(): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            RunFragment::class.java.name -> RunFragment(
                MainViewModel(FakeMainRepositoryAndroid())
            )
            SettingFragment::class.java.name -> SettingFragment()
            SetupFragment::class.java.name -> SetupFragment()
            StatisticFragment::class.java.name -> StatisticFragment(
                StatisticViewModel(FakeMainRepositoryAndroid())
            )
            TrackingFragment::class.java.name -> TrackingFragment(
                MainViewModel(FakeMainRepositoryAndroid())
            )
            else -> super.instantiate(classLoader, className)
        }

    }
}