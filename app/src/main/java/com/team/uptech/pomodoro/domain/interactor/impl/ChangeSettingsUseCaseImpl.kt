package com.team.uptech.pomodoro.domain.interactor.impl

import com.team.uptech.pomodoro.data.model.Pomodoro
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.domain.interactor.ChangeSettingsUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created on 22.05.17.
 */
class ChangeSettingsUseCaseImpl @Inject constructor(val pomodoroRepository: PomodoroRepository) : ChangeSettingsUseCase {

    override fun getIsInfinite(): Single<Boolean> {
        return Single.create<Boolean> { sb ->
            pomodoroRepository.getIsInfiniteMode().subscribe({
                sb.onSuccess(it)
            }, {
                sb.onError(it)
            })
        }
    }

    override fun getPomodoroTypeTime(type: Pomodoro): Single<Int> {
        return Single.create<Int> { sb ->
            pomodoroRepository.getPomodoroTypeTime(type).subscribe({
                sb.onSuccess(it)
            }, {
                sb.onError(it)
            })
        }
    }

    override fun changeIsRunningInfinite(isInfinite: Boolean) = pomodoroRepository.saveIsInfiniteMode(isInfinite)

    override fun changeTypeTime(type: Pomodoro, time: Int) = pomodoroRepository.savePomodoroTypeTime(type, time)
}