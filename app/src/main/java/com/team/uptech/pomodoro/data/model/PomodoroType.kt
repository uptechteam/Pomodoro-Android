package com.team.uptech.pomodoro.data.model

/**
 * Created on 04.05.17.
 */
enum class PomodoroType(var time: Int) {
    WORK(10),
    BREAK(5),
    NOT_WORKING(0)// timer is not working
}