package com.team.uptech.pomodoro.presentation.ui

/**
 * Created on 18.05.17.
 */
interface SettingsView : BaseView {
    fun showDialog(messageId: Int, shouldCloseScreen: Boolean)
    fun showWorkTime(workTime: Int)
    fun showRelaxTime(relaxTime: Int)
}