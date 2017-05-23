package com.team.uptech.pomodoro.domain.interactor

import com.team.uptech.pomodoro.domain.model.PomodoroTypeDomain
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created on 22.05.17.
 */
interface ChangeTimeUseCase {
    fun getPomodoroTypeTime(type: PomodoroTypeDomain): Single<Int>
    fun getIsInfinite(): Boolean
    fun changeTypeTime(type: PomodoroTypeDomain, time: Int): Completable
    fun changeInfinity(isInfinite: Boolean): Completable
}