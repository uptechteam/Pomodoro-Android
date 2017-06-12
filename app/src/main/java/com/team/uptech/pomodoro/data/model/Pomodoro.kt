package com.team.uptech.pomodoro.data.model

/**
 * Created on 04.05.17.
 */

/**
 *  There are 3 states of the app:
 *  1. Timer running with a type WORK
 *  2. Timer running with a type BREAK
 *  3. Timer is not running
 */
enum class Pomodoro(var time: Int) {
    WORK(10),
    BREAK(5),
    NOT_WORKING(0)// timer is not working
}