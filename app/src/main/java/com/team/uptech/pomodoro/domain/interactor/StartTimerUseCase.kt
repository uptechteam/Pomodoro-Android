package com.team.uptech.pomodoro.domain.interactor

import com.team.uptech.pomodoro.data.model.Pomodoro
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created on 27.04.17.
 */
interface StartTimerUseCase {
    fun changePomodoroType(): Single<Pomodoro>
    fun startNew(): Maybe<Pomodoro>
    fun getCurrentPomodoro(): Single<Pomodoro>
}