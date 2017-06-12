package com.team.uptech.pomodoro.domain.interactor.impl

import android.util.Log
import com.team.uptech.pomodoro.data.model.Pomodoro
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.domain.interactor.TimerUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created on 25.05.17.
 */

class TimerUseCaseImpl(val pomodoroRepository: PomodoroRepository) : TimerUseCase {

    private var subject: PublishSubject<Int>? = PublishSubject.create()
    private var tickDisposable: Disposable? = null

    override fun startTimer(timerTime: Int) {
        tickDisposable?.dispose()
        tickDisposable = tick(timerTime)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    subject?.onNext(1) //to bypass the function delay of the Observable.interval
                }
                .subscribe({ response ->
                    subject?.onNext(response)
                }, { error ->
                    subject?.onError(error)
                    Log.e("TimerUseCaseImpl", "", error)
                }, {
                    subject?.onComplete()
                })
    }

    override fun stopTimer() {
        tickDisposable?.dispose()
        pomodoroRepository.saveCurrentPomodoro(Pomodoro.NOT_WORKING).subscribe()
    }

    override fun getCurrentProgress() = subject

    override fun timerFinished() {
        subject = PublishSubject.create()
    }

    private fun tick(timerTime: Int) = Observable.interval(1, TimeUnit.SECONDS)
            .take(timerTime.toLong())
            .map { it.toInt() + 2 } //start right after subscribing
}
