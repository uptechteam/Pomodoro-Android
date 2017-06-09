package com.team.uptech.pomodoro.presentation.presenter.impl

import android.util.Log
import com.team.uptech.pomodoro.domain.interactor.TimerUseCase
import com.team.uptech.pomodoro.presentation.presenter.TimerPresenter
import com.team.uptech.pomodoro.presentation.ui.ProgressListener
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created on 01.06.17.
 */
class TimerPresenterImpl @Inject constructor(val timerUseCase: TimerUseCase) : TimerPresenter {

    private var progressListener: ProgressListener? = null
    private var tickDisposable: Disposable? = null

    override fun setProgressListener(listener: ProgressListener?) {
        this.progressListener = listener
    }

    override fun onStartTimerClicked(timerTime: Int) {
        timerUseCase.startTimer(timerTime)
        tickDisposable = timerUseCase.getTimerSubject()
                ?.subscribe({ sb ->
                    Log.d("LOOOL", "sb = " + sb)
                    progressListener?.updateTimerProgress(sb, timerTime)
                }, { error ->
                    Log.e("TimerService", "", error)
                }, {
                    progressListener?.timerFinished()
                    timerUseCase.timerFinished()
                })
    }

    override fun onStopTimerClicked() {
        timerUseCase.stopTimer()
    }

    override fun onTimerFinished() {
        timerUseCase.timerFinished()
    }


}