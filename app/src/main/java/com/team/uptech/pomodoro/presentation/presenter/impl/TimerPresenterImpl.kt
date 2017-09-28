package com.team.uptech.pomodoro.presentation.presenter.impl

import android.util.Log
import com.team.uptech.pomodoro.domain.interactor.TimerUseCase
import com.team.uptech.pomodoro.presentation.presenter.TimerPresenter
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created on 01.06.17.
 */
class TimerPresenterImpl @Inject constructor(val timerUseCase: TimerUseCase) : TimerPresenter {

    private var progressListenerSubject: PublishSubject<Int>? = null
    private var tickDisposable: Disposable? = null

    override fun getCurrentProgress() = progressListenerSubject

    override fun onStartTimerClicked(timerTime: Int) {
        timerUseCase.startTimer(timerTime)
        progressListenerSubject = PublishSubject.create()
        tickDisposable = timerUseCase.getCurrentProgress()
                ?.subscribe({ sb ->
                    progressListenerSubject?.onNext(sb)
                }, { error ->
                    Log.e("TimerPresenterImpl", "", error)
                }, {
                    progressListenerSubject?.onComplete()
                    timerUseCase.timerFinished()
                })
    }

    override fun onStopTimerClicked() = timerUseCase.stopTimer()
}