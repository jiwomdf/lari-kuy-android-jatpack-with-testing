package com.programmergabut.larikuy.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.other.Constants
import com.programmergabut.larikuy.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToSetUpFragmentIfNeeded()
        navigateToTrackingFragmentIfNeeded(intent)

        setSupportActionBar(toolbar)
        setToolBarName()
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        bottomNavigationView.setOnNavigationItemReselectedListener {
            /* NO-OP */
        }

        navHostFragment.findNavController()
            .addOnDestinationChangedListener{_, destination, _ ->
                when(destination.id){
                    R.id.settingFragment, R.id.runFragment, R.id.statisticFragment -> bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
    }

    private fun navigateToSetUpFragmentIfNeeded() {
        val name = sharedPref.getString(Constants.KEY_NAME, "") ?: ""
        val weight = sharedPref.getFloat(Constants.KEY_WEIGHT, 0f)

        if(name == "" && weight == 0f)
            navHostFragment.findNavController().navigate(R.id.setupFragment)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?){
        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT){
            navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }

    private fun setToolBarName() {
        tvToolbarTitle.text = "Let's Go ${sharedPref.getString(Constants.KEY_NAME, "Buddy")}"
    }
}