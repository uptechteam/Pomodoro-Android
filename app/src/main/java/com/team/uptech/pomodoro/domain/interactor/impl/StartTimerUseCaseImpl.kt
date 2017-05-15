package com.team.uptech.pomodoro.domain.interactor.impl

import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import com.team.uptech.pomodoro.domain.mapper.mapToDataModel
import com.team.uptech.pomodoro.domain.mapper.mapToDomainModel
import com.team.uptech.pomodoro.domain.mapper.mapToPresentationModel
import com.team.uptech.pomodoro.domain.model.PomodoroTypeDomain
import com.team.uptech.pomodoro.presentation.model.Pomodoro
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created on 27.04.17.
 */
class StartTimerUseCaseImpl @Inject constructor(val pomodoroRepository: PomodoroRepository) : StartTimerUseCase {

    override fun changeStartStop(): Observable<Pomodoro> {
        return Observable.create<Pomodoro> { sb ->
            pomodoroRepository.getPomodoro()
                    .subscribe {
                        val currentPomodoro = mapToDomainModel(it)
                        currentPomodoro.isRunning = !currentPomodoro.isRunning
                        currentPomodoro.type =
                                if (currentPomodoro.type == PomodoroTypeDomain.WORK)
                                    PomodoroTypeDomain.BREAK
                                else PomodoroTypeDomain.WORK
                        sb.onNext(mapToPresentationModel(currentPomodoro))
                        pomodoroRepository.savePomodoro(mapToDataModel(currentPomodoro)).subscribe()
                        sb.onComplete()
                    }
        }.subscribeOn(Schedulers.io())
    }
}