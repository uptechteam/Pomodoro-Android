package com.team.uptech.pomodoro

import android.content.Context
import android.preference.PreferenceManager


/**
 * Created on 26.04.17.
 */
class AppPreferences(context: Context) {

    init {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }
}