package com.team.uptech.pomodoro.presentation.presenter

import com.team.uptech.pomodoro.data.model.Pomodoro
import com.team.uptech.pomodoro.presentation.ui.view.SettingsView

/**
 * Created on 18.05.17.
 */
interface SettingsPresenter : BasePresenter<SettingsView> {
    fun getPomodoroTime(pomodoro: Pomodoro)
    fun getIsInfinite()
    fun onWorkTimeChanged(time: Int)
    fun onRelaxTimeChanged(time: Int)
    fun onInfinityChanged(isInfinite: Boolean)
}
