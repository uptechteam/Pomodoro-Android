package com.team.uptech.pomodoro.presentation.presenter

import com.team.uptech.pomodoro.presentation.model.PomodoroType
import com.team.uptech.pomodoro.presentation.ui.SettingsView

/**
 * Created on 18.05.17.
 */
interface SettingsPresenter : BasePresenter<SettingsView> {
    fun getPomodoroTime(pomodoroType: PomodoroType)
    fun getIsInfinite()
    fun onWorkTimeChanged(time: Int)
    fun onRelaxTimeChanged(time: Int)
    fun onInfinityChanged(isInfinite: Boolean)
}
