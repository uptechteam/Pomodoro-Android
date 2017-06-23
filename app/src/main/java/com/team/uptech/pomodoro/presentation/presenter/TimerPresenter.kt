package com.team.uptech.pomodoro.presentation.presenter

import io.reactivex.subjects.PublishSubject

/**
 * Created on 01.06.17.
 */
interface TimerPresenter {
    fun onStartTimerClicked(timerTime: Int)
    fun onStopTimerClicked()
    fun getCurrentProgress(): PublishSubject<Int>?
}