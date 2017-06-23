package com.team.uptech.pomodoro.domain.interactor.impl

import android.util.Log
import com.team.uptech.pomodoro.data.model.Pomodoro
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created on 27.04.17.
 */
class StartTimerUseCaseImpl @Inject constructor(val pomodoroRepository: PomodoroRepository) : StartTimerUseCase {

    override fun getCurrentPomodoro(): Single<Pomodoro> {
        return Single.create<Pomodoro> { sb ->
            pomodoroRepository.getCurrentPomodoro()
                    .subscribe({
                        sb.onSuccess(it)
                    }, {
                        Log.e("Error", it.toString())
                    })
        }.subscribeOn(Schedulers.io())
    }

    /**
     * onSuccess -> isInfiniteMode() enabled
     */
    override fun startNew(): Maybe<Pomodoro> {
        return Maybe.create<Pomodoro> { sb ->
            pomodoroRepository.getCurrentPomodoro()
                    .subscribe({
                        var currentPomodoro = it
                        currentPomodoro =
                                if (currentPomodoro == Pomodoro.WORK)
                                    Pomodoro.BREAK
                                else
                                    Pomodoro.WORK
                        pomodoroRepository.getIsInfiniteMode()
                                .subscribe({
                                    if (it) {
                                        sb.onSuccess(currentPomodoro)
                                        pomodoroRepository.saveCurrentPomodoro(currentPomodoro).subscribe()
                                    } else {
                                        sb.onComplete()
                                    }
                                }, {
                                    Log.e("StartTimerUseCase", "", it)
                                })
                    }, {
                        Log.e("Error", it.toString())
                    })
        }.subscribeOn(Schedulers.io())
    }

    override fun changePomodoroType(): Single<Pomodoro> {
        return Single.create<Pomodoro> { sb ->
            pomodoroRepository.getCurrentPomodoro()
                    .subscribe({
                        var currentPomodoro = it
                        currentPomodoro =
                                if (currentPomodoro == Pomodoro.WORK)
                                    Pomodoro.BREAK
                                else
                                    Pomodoro.WORK
                        sb.onSuccess(currentPomodoro)
                        pomodoroRepository.saveCurrentPomodoro(currentPomodoro).subscribe()
                    }, {
                        sb.onError(it)
                        Log.e("Error", it.toString())
                    })
        }.subscribeOn(Schedulers.io())
    }
}