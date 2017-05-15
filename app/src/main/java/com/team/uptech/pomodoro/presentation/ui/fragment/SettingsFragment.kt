package com.team.uptech.pomodoro.presentation.ui.fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import com.team.uptech.pomodoro.R

/**
 * Created on 27.04.17.
 */
class SettingsFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
    }
}