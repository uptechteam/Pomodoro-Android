package com.team.uptech.pomodoro.domain.interactor.impl

import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.domain.interactor.ChangeTimeUseCase
import com.team.uptech.pomodoro.domain.model.PomodoroTypeDomain
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created on 22.05.17.
 */
class ChangeTimeUseCaseImpl @Inject constructor(val pomodoroRepository: PomodoroRepository) : ChangeTimeUseCase {
    override fun changeInfinity(isInfinite: Boolean) = pomodoroRepository.setIsInfinite(isInfinite)

    override fun getIsInfinite() = pomodoroRepository.getIsInfinite()

    override fun getPomodoroTypeTime(type: PomodoroTypeDomain) = Single.just(pomodoroRepository.getPomodoroTime(type.toString()))

    override fun changeTypeTime(type: PomodoroTypeDomain, time: Int) = Completable.create { pomodoroRepository.saveTime(type.toString(), time).subscribe() }
}