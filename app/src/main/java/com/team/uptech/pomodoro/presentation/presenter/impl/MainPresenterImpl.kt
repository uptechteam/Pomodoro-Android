package com.team.uptech.pomodoro.presentation.presenter.impl

import com.team.uptech.pomodoro.PerActivity
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.ui.PomodoroView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created on 27.04.17.
 */
@PerActivity
class MainPresenterImpl @Inject constructor(val startTimerUseCase: StartTimerUseCase) : MainPresenter {
    private var pomodoroView: PomodoroView? = null

    override fun bind(view: PomodoroView) {
        this.pomodoroView = view
    }

    override fun unbind() {
        pomodoroView?.showMessage("onDestroy()")
        this.pomodoroView = null
    }

    override fun onStartStopClicked() {
        startTimerUseCase.changeStartStop()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { pomodoroView?.showProgress() }
                .doAfterTerminate { pomodoroView?.hideProgress() }
                .subscribe({
                    if (it.isRunning) {
                        pomodoroView?.hideTimer()
                    } else {
                        pomodoroView?.showTimer()
                    }
                }, { pomodoroView?.showError(it.toString()) })
    }
}