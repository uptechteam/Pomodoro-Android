package com.team.uptech.pomodoro.presentation.presenter

import com.team.uptech.pomodoro.presentation.ui.ProgressListener

/**
 * Created on 01.06.17.
 */
interface TimerPresenter {
    fun onStartTimerClicked(timerTime: Int)
    fun onStopTimerClicked()
    fun onTimerFinished()
    fun setProgressListener(listener: ProgressListener?)
}