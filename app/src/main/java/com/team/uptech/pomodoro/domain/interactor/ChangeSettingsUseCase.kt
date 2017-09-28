package com.team.uptech.pomodoro.domain.interactor

import com.team.uptech.pomodoro.data.model.Pomodoro
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created on 22.05.17.
 */
interface ChangeSettingsUseCase {
    fun getPomodoroTypeTime(type: Pomodoro): Single<Int>
    fun getIsInfinite(): Single<Boolean>
    fun changeTypeTime(type: Pomodoro, time: Int): Completable
    fun changeIsRunningInfinite(isInfinite: Boolean): Completable
}