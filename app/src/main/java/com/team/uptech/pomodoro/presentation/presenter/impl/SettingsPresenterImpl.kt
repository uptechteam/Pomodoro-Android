package com.team.uptech.pomodoro.presentation.presenter.impl

import android.util.Log
import com.team.uptech.pomodoro.dagger.scope.PerActivity
import com.team.uptech.pomodoro.data.model.PomodoroType
import com.team.uptech.pomodoro.domain.interactor.ChangeSettingsUseCase
import com.team.uptech.pomodoro.presentation.presenter.SettingsPresenter
import com.team.uptech.pomodoro.presentation.ui.view.SettingsView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created on 18.05.17.
 */
@PerActivity
class SettingsPresenterImpl @Inject constructor(val changeSettingsUseCase: ChangeSettingsUseCase) : SettingsPresenter {

    private var settingsView: SettingsView? = null

    override fun getPomodoroTime(pomodoroType: PomodoroType) {
        changeSettingsUseCase.getPomodoroTypeTime(PomodoroType.valueOf(pomodoroType.toString()))
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

    override fun getIsInfinite() {
        changeSettingsUseCase.getIsInfinite().subscribe({
            settingsView?.showIsInfinite(it)
        }, { Log.e("SettingsPresenter", "", it) })
    }

    override fun onWorkTimeChanged(time: Int) {
        changeSettingsUseCase.changeTypeTime(PomodoroType.WORK, time).subscribe()
    }

    override fun onRelaxTimeChanged(time: Int) {
        changeSettingsUseCase.changeTypeTime(PomodoroType.BREAK, time).subscribe()
    }

    override fun onInfinityChanged(isInfinite: Boolean) {
        changeSettingsUseCase.changeInfinity(isInfinite).subscribe()
    }

    override fun bind(view: SettingsView) {
        settingsView = view
    }

    override fun unbind() {
        settingsView = null
    }
}