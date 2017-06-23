package com.team.uptech.pomodoro.utils

import android.content.Context
import com.team.uptech.pomodoro.App
import com.team.uptech.pomodoro.dagger.AppComponent

/**
 * Created on 15.05.17.
 */
fun Context.getAppComponent(): AppComponent = (applicationContext as App).appComponent

