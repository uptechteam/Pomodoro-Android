package com.team.uptech.pomodoro.domain.interactor.impl

import android.util.Log
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import com.team.uptech.pomodoro.domain.mapper.mapToDataModel
import com.team.uptech.pomodoro.domain.mapper.mapToDomainModel
import com.team.uptech.pomodoro.domain.mapper.mapToPresentationModel
import com.team.uptech.pomodoro.domain.model.PomodoroTypeDomain
import com.team.uptech.pomodoro.presentation.model.Pomodoro
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created on 27.04.17.
 */
class StartTimerUseCaseImpl @Inject constructor(val pomodoroRepository: PomodoroRepository) : StartTimerUseCase {

    override fun changeStartStop(): Single<Pomodoro> {
        return Single.create<Pomodoro> { sb ->
            pomodoroRepository.getPomodoro()
                    .subscribe({
                        val currentPomodoro = mapToDomainModel(it)
                        currentPomodoro.isRunning = !currentPomodoro.isRunning
                        currentPomodoro.type =
                                if (currentPomodoro.type == PomodoroTypeDomain.WORK)
                                    PomodoroTypeDomain.BREAK
                                else
                                    PomodoroTypeDomain.WORK

                        sb.onSuccess(mapToPresentationModel(currentPomodoro))
                    }, {
                        Log.e("Error", it.toString())
                    })
        }.doAfterSuccess {
            pomodoroRepository.savePomodoro(mapToDataModel(mapToDomainModel(it))).subscribe()
        }
                .subscribeOn(Schedulers.io())
    }
}