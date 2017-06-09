package com.team.uptech.pomodoro.data.repository

import com.team.uptech.pomodoro.data.model.PomodoroType
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created on 28.04.17.
 */
interface PomodoroRepository {
    fun getCurrentPomodoro(): Single<PomodoroType?> //null - timer is not running
    fun saveCurrentPomodoro(pomodoro: PomodoroType?): Completable
    fun getPomodoroTypeTime(pomodoro: PomodoroType): Single<Int>
    fun savePomodoroTypeTime(pomodoro: PomodoroType, time: Int): Completable
    fun getIsInfiniteMode(): Single<Boolean>
    fun saveIsInfiniteMode(infinite: Boolean): Completable
}