package com.team.uptech.pomodoro.domain.mapper

/**
 * Created on 04.05.17.
 */
//fun mapToDataModel(pomodoro: PomodoroDomain): PomodoroData {
//    val result = PomodoroData(pomodoro.type.toString(), pomodoro.type.time, pomodoro.isRunning)
//    return result
//}
//
//fun mapToDomainModel(pomodoro: PomodoroData): PomodoroDomain {
//    val typeEnum = PomodoroType.valueOf(pomodoro.type)
//    typeEnum.time = pomodoro.time
//    val result = PomodoroDomain(pomodoro.isRunning, typeEnum)
//    return result
//}
//
//fun mapToDomainModel(pomodoro: Pomodoro): PomodoroDomain {
//    val typeEnum = PomodoroType.valueOf(pomodoro.type.toString())
//    typeEnum.time = pomodoro.type.time
//    val result = PomodoroDomain(pomodoro.isRunning, typeEnum)
//    return result
//}
//
//fun mapToPresentationModel(pomodoro: PomodoroDomain): Pomodoro {
//    val typeEnum = PomodoroType.valueOf(pomodoro.type.toString())
//    typeEnum.time = pomodoro.type.time
//    val result = Pomodoro(pomodoro.isRunning, typeEnum)
//    return result
//}