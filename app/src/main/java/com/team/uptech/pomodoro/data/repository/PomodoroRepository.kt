package com.team.uptech.pomodoro.data.repository

import com.team.uptech.pomodoro.data.model.Pomodoro
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created on 28.04.17.
 */
interface PomodoroRepository {
    fun getCurrentPomodoro(): Single<Pomodoro>
    fun saveCurrentPomodoro(pomodoro: Pomodoro?): Completable
    fun getPomodoroTypeTime(pomodoro: Pomodoro): Single<Int>
    fun savePomodoroTypeTime(pomodoro: Pomodoro, time: Int): Completable
    fun getIsInfiniteMode(): Single<Boolean>
    fun saveIsInfiniteMode(infinite: Boolean): Completable
}