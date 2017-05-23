package com.team.uptech.pomodoro.data.repository

import com.team.uptech.pomodoro.data.model.PomodoroData
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created on 28.04.17.
 */
interface PomodoroRepository {
    fun getPomodoroType(): String
    fun getPomodoro(): Single<PomodoroData>
    fun getPomodoroTime(pomodoroType: String): Int
    fun savePomodoro(pomodoro: PomodoroData): Completable
    fun getIsInfinite(): Boolean
    fun setIsInfinite(infinite: Boolean): Completable
}