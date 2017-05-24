package com.team.uptech.pomodoro.presentation.ui.view

/**
 * Created on 28.04.17.
 */
interface BaseView {
    fun showProgress()
    fun hideProgress()
    fun showError(message: String)
}