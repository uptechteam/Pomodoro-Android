package com.team.uptech.pomodoro.domain.interactor.impl

import android.util.Log
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import com.team.uptech.pomodoro.domain.mapper.mapToDataModel
import com.team.uptech.pomodoro.domain.mapper.mapToDomainModel
import com.team.uptech.pomodoro.domain.mapper.mapToPresentationModel
import com.team.uptech.pomodoro.domain.model.PomodoroTypeDomain
import com.team.uptech.pomodoro.presentation.model.Pomodoro
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created on 27.04.17.
 */
class StartTimerUseCaseImpl @Inject constructor(val pomodoroRepository: PomodoroRepository) : StartTimerUseCase {

    override fun startNew(): Maybe<Pomodoro> {
        return Maybe.create<Pomodoro> { sb ->
            pomodoroRepository.getPomodoro()
                    .subscribe({
                        val currentPomodoro = mapToDomainModel(it)
                        currentPomodoro.type =
                                if (currentPomodoro.type == PomodoroTypeDomain.WORK)
                                    PomodoroTypeDomain.BREAK
                                else
                                    PomodoroTypeDomain.WORK
                        if (pomodoroRepository.getIsInfinite()) {
                            sb.onSuccess(mapToPresentationModel(currentPomodoro))
                        } else {
                            currentPomodoro.isRunning = !currentPomodoro.isRunning
                            sb.onComplete()
                        }
                        pomodoroRepository.savePomodoro(mapToDataModel(currentPomodoro)).subscribe()

                    }, {
                        Log.e("Error", it.toString())
                    })
        }.subscribeOn(Schedulers.io())
    }

    override fun changeStartStop(): Single<Pomodoro> {
        return Single.create<Pomodoro> { sb ->
            pomodoroRepository.getPomodoro()
                    .subscribe({
                        sb.onSuccess(mapToPresentationModel(mapToDomainModel(it)))
                    }, {
                        Log.e("Error", it.toString())
                    })
        }.doAfterSuccess {
            val currentPomodoro = mapToDomainModel(it)
            currentPomodoro.isRunning = !currentPomodoro.isRunning
            pomodoroRepository.savePomodoro(mapToDataModel(currentPomodoro)).subscribe()
        }.subscribeOn(Schedulers.io())
    }
}