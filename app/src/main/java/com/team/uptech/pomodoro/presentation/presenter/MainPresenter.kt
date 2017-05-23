package com.team.uptech.pomodoro.presentation.presenter

import com.team.uptech.pomodoro.presentation.ui.PomodoroView

/**
 * Created on 27.04.17.
 */
interface MainPresenter: BasePresenter<PomodoroView>{
    fun onStartStopClicked()
    fun onTimerFinished()
}
