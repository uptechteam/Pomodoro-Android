package com.team.uptech.pomodoro.domain.interactor.impl

import android.util.Log
import com.team.uptech.pomodoro.data.model.PomodoroType
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

    override fun getCurrentPomodoro(): Single<PomodoroType> {
        return Single.create<PomodoroType> { sb ->
            pomodoroRepository.getCurrentPomodoro()
                    .subscribe({
                        sb.onSuccess(it)
                    }, {
                        Log.e("Error", it.toString())
                    })
        }.subscribeOn(Schedulers.io())
    }

    override fun startNew(): Maybe<PomodoroType> {
        return Maybe.create<PomodoroType> { sb ->
            pomodoroRepository.getCurrentPomodoro()
                    .subscribe({
                        var currentPomodoro = it
                        currentPomodoro =
                                if (currentPomodoro == PomodoroType.WORK)
                                    PomodoroType.BREAK
                                else
                                    PomodoroType.WORK
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

    override fun changeStartStop(): Single<PomodoroType> {
        return Single.create<PomodoroType> { sb ->
            pomodoroRepository.getCurrentPomodoro()
                    .subscribe({
                        var currentPomodoro = it
                        currentPomodoro =
                                if (currentPomodoro == PomodoroType.WORK)
                                    PomodoroType.BREAK
                                else
                                    PomodoroType.WORK
                        sb.onSuccess(currentPomodoro)
                        pomodoroRepository.saveCurrentPomodoro(currentPomodoro).subscribe()
                    }, {
                        sb.onError(it)
                        Log.e("Error", it.toString())
                    })
        }.subscribeOn(Schedulers.io())
    }
}