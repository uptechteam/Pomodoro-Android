package com.team.uptech.pomodoro.presentation.presenter

import com.team.uptech.pomodoro.presentation.ui.view.MainView

/**
 * Created on 27.04.17.
 */
interface MainPresenter: BasePresenter<MainView>{
    fun onStartStopClicked()
    fun onTimerFinished()
}
