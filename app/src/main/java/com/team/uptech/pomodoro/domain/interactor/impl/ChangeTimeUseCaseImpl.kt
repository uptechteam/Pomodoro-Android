package com.team.uptech.pomodoro.domain.interactor.impl

import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.domain.interactor.ChangeTimeUseCase
import com.team.uptech.pomodoro.domain.mapper.mapToDomainModel
import com.team.uptech.pomodoro.domain.mapper.mapToPresentationModel
import com.team.uptech.pomodoro.presentation.model.PomodoroType
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created on 22.05.17.
 */
class ChangeTimeUseCaseImpl @Inject constructor(val pomodoroRepository: PomodoroRepository) : ChangeTimeUseCase {
    override fun changeInfinity(isInfinite: Boolean) = pomodoroRepository.setIsInfinite(isInfinite)

    override fun getIsInfinite() = pomodoroRepository.getIsInfinite()

    override fun getPomodoroTypeTime(type: PomodoroType) = Single.just(pomodoroRepository.getPomodoroTime(type.toString()))

    override fun changeWorkTime(time: Int) = pomodoroRepository.getPomodoro().map { mapToPresentationModel(mapToDomainModel(it)) }

    override fun changeRelaxTime(time: Int) = pomodoroRepository.getPomodoro().map { mapToPresentationModel(mapToDomainModel(it)) }
}