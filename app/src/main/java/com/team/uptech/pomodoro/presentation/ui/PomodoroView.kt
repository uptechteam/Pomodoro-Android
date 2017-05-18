package com.team.uptech.pomodoro.presentation.ui

import com.team.uptech.pomodoro.presentation.model.Pomodoro

/**
 * Created on 27.04.17.
 */
interface PomodoroView : BaseView {
    fun showTimer(pomodoro: Pomodoro)
    fun hideTimer()
    fun showMessage(message: String)
}