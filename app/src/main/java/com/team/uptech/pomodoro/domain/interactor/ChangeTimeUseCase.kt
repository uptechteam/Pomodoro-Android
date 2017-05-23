package com.team.uptech.pomodoro.domain.interactor

import com.team.uptech.pomodoro.presentation.model.Pomodoro
import com.team.uptech.pomodoro.presentation.model.PomodoroType
import io.reactivex.Single

/**
 * Created on 22.05.17.
 */
interface ChangeTimeUseCase {
    fun getPomodoroTypeTime(type: PomodoroType): Single<Int>
    fun changeWorkTime(time: Int): Single<Pomodoro>
    fun changeRelaxTime(time: Int): Single<Pomodoro>
}