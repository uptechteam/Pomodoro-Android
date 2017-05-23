package com.team.uptech.pomodoro.presentation.presenter.impl

import com.team.uptech.pomodoro.PerActivity
import com.team.uptech.pomodoro.domain.interactor.ChangeTimeUseCase
import com.team.uptech.pomodoro.presentation.model.PomodoroType
import com.team.uptech.pomodoro.presentation.presenter.SettingsPresenter
import com.team.uptech.pomodoro.presentation.ui.SettingsView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created on 18.05.17.
 */
@PerActivity
class SettingsPresenterImpl @Inject constructor(val changeTimeUseCase: ChangeTimeUseCase) : SettingsPresenter {

    private var settingsView: SettingsView? = null

    override fun getPomodoroTime(pomodoroType: PomodoroType) {
        changeTimeUseCase.getPomodoroTypeTime(pomodoroType)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { settingsView?.showProgress() }
                .doAfterTerminate { settingsView?.hideProgress() }
                .subscribe({
                    if (pomodoroType == PomodoroType.WORK)
                        settingsView?.showWorkTime(it)
                    else if (pomodoroType == PomodoroType.BREAK) {
                        settingsView?.showRelaxTime(it)
                    }
                }, {
                    settingsView?.showError(it.toString())
                })
    }

    override fun onChangeWorkTimeClicked(time: Int) {
        changeTimeUseCase.changeWorkTime(time).subscribe()
    }

    override fun onChangeRelaxTimeClicked(time: Int) {
        changeTimeUseCase.changeRelaxTime(time).subscribe()
    }

    override fun bind(view: SettingsView) {
        settingsView = view
    }

    override fun unbind() {
        settingsView = null
    }
}