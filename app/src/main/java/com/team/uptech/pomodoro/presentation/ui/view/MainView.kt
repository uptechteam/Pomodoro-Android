package com.team.uptech.pomodoro.presentation.ui.view

import com.team.uptech.pomodoro.data.model.PomodoroType


/**
 * Created on 27.04.17.
 */
interface MainView : BaseView {
    fun showTimer(pomodoro: PomodoroType)
    fun showCurrentState(pomodoro: PomodoroType?)
    fun hideTimer()
    fun showMessage(message: String)
    fun updateTimerProgress(value: Int, maxValue: Int)
}