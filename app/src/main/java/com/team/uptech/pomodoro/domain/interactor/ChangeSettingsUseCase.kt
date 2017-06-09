package com.team.uptech.pomodoro.domain.interactor

import com.team.uptech.pomodoro.data.model.PomodoroType
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created on 22.05.17.
 */
interface ChangeSettingsUseCase {
    fun getPomodoroTypeTime(type: PomodoroType): Single<Int>
    fun getIsInfinite(): Single<Boolean>
    fun changeTypeTime(type: PomodoroType, time: Int): Completable
    fun changeInfinity(isInfinite: Boolean): Completable
}