package com.team.uptech.pomodoro.presentation.presenter.impl

import android.content.Context
import android.content.Intent
import android.util.Log
import com.team.uptech.pomodoro.dagger.scope.PerActivity
import com.team.uptech.pomodoro.data.model.PomodoroType
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import com.team.uptech.pomodoro.domain.interactor.TimerUseCase
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.service.TimerService
import com.team.uptech.pomodoro.presentation.ui.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created on 27.04.17.
 */
@PerActivity
class MainPresenterImpl @Inject constructor(val context: Context,
                                            val startTimerUseCase: StartTimerUseCase,
                                            val timerUseCase: TimerUseCase) : MainPresenter {

    private var mainView: MainView? = null

    override fun bind(view: MainView) {
        this.mainView = view
    }

    override fun unbind() {
        this.mainView = null
    }

    override fun onStartClicked() {
        startTimerUseCase.changeStartStop()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mainView?.showProgress() }
                .doAfterTerminate { mainView?.hideProgress() }
                .doAfterSuccess {
                    timerUseCase.getTimerSubject()
                            ?.subscribe({ sb ->
                                if (it != null) {
                                    Log.d("LOOOL", "MainActivity sb = " + sb)
                                    mainView?.updateTimerProgress(sb, it.time)
                                }
                            }, { error ->
                                Log.d("MainActivity", "", error)
                            }, {
                                timerUseCase.timerFinished()
                                Log.d("LOOOL", "MainActivity getTimerSubject onComplete")
                            })
                }
                .subscribe({
                    mainView?.showTimer(it)
                    startTimerService(it)
                }, {
                    mainView?.showError(it.toString())
                })
    }

    override fun onStopClicked() {
        startTimerUseCase.changeStartStop()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mainView?.showProgress() }
                .doAfterTerminate { mainView?.hideProgress() }
                .subscribe({
                    mainView?.hideTimer()
                    stopTimerService()
                }, {
                    mainView?.showError(it.toString())
                })
    }

    override fun onTimerFinished() {
        startTimerUseCase.startNew()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mainView?.showProgress() }
                .doAfterTerminate { mainView?.hideProgress() }
                .doAfterSuccess {
                    timerUseCase.getTimerSubject()
                            ?.subscribe({ sb ->
                                Log.d("LOOOL", "MainActivity sb = " + sb)
                                mainView?.updateTimerProgress(sb, it.time)
                            }, { error ->
                                Log.d("MainActivity", "", error)
                            }, {
                                timerUseCase.timerFinished()
                                Log.d("LOOOL", "MainActivity getTimerSubject onComplete")
                            })
                }
                .subscribe({
                    mainView?.showTimer(it)
                    startTimerService(it)
                }, { error ->
                    mainView?.showError(error.toString())
                }, {
                    mainView?.hideTimer()
//                    stopTimerService()
                })
    }

    private fun startTimerService(pomodoro: PomodoroType?) {
        val serviceIntent = Intent(context, TimerService::class.java)
        serviceIntent.putExtra(TimerService.timerTime, pomodoro?.time)
        serviceIntent.putExtra(TimerService.timerType, pomodoro?.name)
        context.startService(serviceIntent)
    }

    private fun stopTimerService() {
        context.stopService(Intent(context, TimerService::class.java))
    }

    override fun getCurrentPomodoro() {
        startTimerUseCase.getCurrentPomodoro()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mainView?.showCurrentState(it)
                }, { error ->
                    mainView?.showError(error.toString())
                })
    }
}