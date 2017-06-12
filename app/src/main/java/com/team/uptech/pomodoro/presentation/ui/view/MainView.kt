package com.team.uptech.pomodoro.presentation.ui.view

import com.team.uptech.pomodoro.data.model.Pomodoro


/**
 * Created on 27.04.17.
 */
interface MainView : BaseView {
    fun showTimer(pomodoro: Pomodoro)
    fun showCurrentState(pomodoro: Pomodoro)
    fun hideTimer()
    fun showMessage(message: String)
    fun updateTimerProgress(value: Int, maxValue: Int)
}