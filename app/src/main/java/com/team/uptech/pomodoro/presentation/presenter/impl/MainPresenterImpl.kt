package com.team.uptech.pomodoro.presentation.presenter.impl

import com.team.uptech.pomodoro.dagger.scope.PerActivity
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.ui.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created on 27.04.17.
 */
@PerActivity
class MainPresenterImpl @Inject constructor(val startTimerUseCase: StartTimerUseCase) : MainPresenter {

    private var mainView: MainView? = null

    override fun bind(view: MainView) {
        this.mainView = view
    }

    override fun unbind() {
        mainView?.showMessage("onDestroy()")
        this.mainView = null
    }

    override fun onStartStopClicked() {
        startTimerUseCase.changeStartStop()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mainView?.showProgress() }
                .doAfterTerminate { mainView?.hideProgress() }
                .subscribe({
                    if (it.isRunning) {
                        mainView?.hideTimer()
                    } else {
                        mainView?.showTimer(it)
                    }
                }, { mainView?.showError(it.toString()) })
    }

    override fun onTimerFinished() {
        startTimerUseCase.startNew()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mainView?.showProgress() }
                .doAfterTerminate { mainView?.hideProgress() }
                .subscribe({ sb ->
                    mainView?.showTimer(sb)
                }, { error ->
                    mainView?.showError(error.toString())
                }, {
                    mainView?.hideTimer()
                })
    }

    override fun getCurrentPomodoro() {
        startTimerUseCase.getCurrentPomodoro()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isRunning) {
                        mainView?.showCurrentState(it)
                    } else {
                        mainView?.showMessage(it.type.toString() + " isRunning = false")
                    }
                }, { error ->
                    mainView?.showError(error.toString())
                })
    }
}