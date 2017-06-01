package com.team.uptech.pomodoro.domain.interactor

import io.reactivex.subjects.PublishSubject

/**
 * Created on 01.06.17.
 */
interface TimerUseCase {
    fun startTimer(timerTime: Int)
    fun stopTimer()
    fun getTimerSubject(): PublishSubject<Int>?
    fun timerFinished()
}