package com.team.uptech.pomodoro.domain.mapper

import com.team.uptech.pomodoro.data.model.PomodoroData
import com.team.uptech.pomodoro.data.model.PomodoroTypeData
import com.team.uptech.pomodoro.domain.model.PomodoroDomain
import com.team.uptech.pomodoro.domain.model.PomodoroTypeDomain
import com.team.uptech.pomodoro.presentation.model.Pomodoro
import com.team.uptech.pomodoro.presentation.model.PomodoroType

/**
 * Created on 04.05.17.
 */
fun mapToDataModel(pomodoro: PomodoroDomain): PomodoroData {
    val typeEnum = PomodoroTypeData.valueOf(pomodoro.type.toString())
    val result = PomodoroData(pomodoro.isRunning, typeEnum)
    return result
}

fun mapToDomainModel(pomodoro: PomodoroData): PomodoroDomain {
    val typeEnum = PomodoroTypeDomain.valueOf(pomodoro.type.toString())
    val result = PomodoroDomain(pomodoro.isRunning, typeEnum)
    return result
}

fun mapToDomainModel(pomodoro: Pomodoro): PomodoroDomain {
    val typeEnum = PomodoroTypeDomain.valueOf(pomodoro.type.toString())
    val result = PomodoroDomain(pomodoro.isRunning, typeEnum)
    return result
}

fun mapToPresentationModel(pomodoro: PomodoroDomain): Pomodoro {
    val typeEnum = PomodoroType.valueOf(pomodoro.type.toString())
    val result = Pomodoro(pomodoro.isRunning, typeEnum)
    return result
}