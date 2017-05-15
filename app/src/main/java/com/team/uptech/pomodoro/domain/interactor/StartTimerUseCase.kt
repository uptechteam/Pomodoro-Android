package com.team.uptech.pomodoro.domain.interactor

import com.team.uptech.pomodoro.presentation.model.Pomodoro
import io.reactivex.Observable

/**
 * Created on 27.04.17.
 */
interface StartTimerUseCase {
    fun changeStartStop(): Observable<Pomodoro>
}