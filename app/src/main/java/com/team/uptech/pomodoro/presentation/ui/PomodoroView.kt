package com.team.uptech.pomodoro.presentation.ui

/**
 * Created on 27.04.17.
 */
interface PomodoroView : BaseView {
    fun showTimer()
    fun hideTimer()
    fun showMessage(message: String)
}