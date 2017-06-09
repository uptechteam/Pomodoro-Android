package com.team.uptech.pomodoro.presentation.ui

/**
 * Created on 01.06.17.
 */
interface ProgressListener {
    fun updateTimerProgress(value: Int, maxValue: Int)
    fun timerFinished()
}