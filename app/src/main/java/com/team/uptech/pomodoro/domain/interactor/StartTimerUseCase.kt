package com.team.uptech.pomodoro.domain.interactor

import com.team.uptech.pomodoro.data.model.PomodoroType
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created on 27.04.17.
 */
interface StartTimerUseCase {
    fun changeStartStop(): Single<PomodoroType>
    fun startNew(): Maybe<PomodoroType>
    fun getCurrentPomodoro(): Single<PomodoroType?>
}