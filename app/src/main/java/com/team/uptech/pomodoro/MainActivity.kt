package com.team.uptech.pomodoro

import android.os.Bundle

/**
 * Created on 26.04.17.
 */
class MainActivity : BaseActivity() {

    override fun getContentView() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PomodoroApplication.component.inject(this)
    }
}
