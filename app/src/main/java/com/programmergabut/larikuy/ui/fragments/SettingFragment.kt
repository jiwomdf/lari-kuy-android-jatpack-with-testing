package com.programmergabut.larikuy.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.other.Constants.KEY_NAME
import com.programmergabut.larikuy.other.Constants.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setting.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment: Fragment(R.layout.fragment_setting) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFieldFromSharedPref()

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharePref()

            if(success)
                Snackbar.make(view, "Saved changes", Snackbar.LENGTH_SHORT).show()
            else
                Snackbar.make(view, "Please fill out all the fields", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun loadFieldFromSharedPref(){
        val name = sharedPref.getString(KEY_NAME, "") ?: ""
        val weight = sharedPref.getFloat(KEY_WEIGHT, 55f)

        etName.setText(name)
        etWeight.setText(weight.toString())
    }

    private fun applyChangesToSharePref(): Boolean{
        val nameText = etName.text.toString()
        val weightText = etWeight.text.toString()

        if(nameText.isEmpty() || weightText.isEmpty()){
            return false
        }

        sharedPref.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT, weightText.toFloat())
            .apply()

        val toolbartext = "Let's Go $nameText"
        requireActivity().tvToolbarTitle.text = toolbartext

        return true
    }

}