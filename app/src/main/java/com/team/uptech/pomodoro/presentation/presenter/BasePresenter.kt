package com.team.uptech.pomodoro.presentation.presenter

import com.team.uptech.pomodoro.presentation.ui.view.BaseView

/**
 * Created on 27.04.17.
 */
interface BasePresenter<in V : BaseView> {
    fun bind(view: V)
    fun unbind()
}