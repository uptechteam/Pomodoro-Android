package com.team.uptech.pomodoro.presentation.presenter.impl

import android.util.Log
import com.team.uptech.pomodoro.dagger.scope.PerActivity
import com.team.uptech.pomodoro.data.model.Pomodoro
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

    override fun getPomodoroTime(pomodoro: Pomodoro) {
        changeSettingsUseCase.getPomodoroTypeTime(Pomodoro.valueOf(pomodoro.toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { settingsView?.showProgress() }
                .doAfterTerminate { settingsView?.hideProgress() }
                .subscribe({
                    if (pomodoro == Pomodoro.WORK)
                        settingsView?.showWorkTime(it)
                    else if (pomodoro == Pomodoro.BREAK) {
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
        changeSettingsUseCase.changeTypeTime(Pomodoro.WORK, time).subscribe()
    }

    override fun onRelaxTimeChanged(time: Int) {
        changeSettingsUseCase.changeTypeTime(Pomodoro.BREAK, time).subscribe()
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