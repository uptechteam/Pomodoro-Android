package com.team.uptech.pomodoro

import android.content.Context

/**
 * Created on 15.05.17.
 */
fun Context.getAppComponent(): AppComponent = (applicationContext as PomodoroApplication).appComponent

