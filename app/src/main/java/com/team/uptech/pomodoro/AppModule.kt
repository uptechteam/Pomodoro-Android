package com.team.uptech.pomodoro

import android.content.Context
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.data.repository.impl.PomodoroRepositoryPrefsImpl
import com.team.uptech.pomodoro.domain.interactor.ChangeTimeUseCase
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import com.team.uptech.pomodoro.domain.interactor.impl.ChangeTimeUseCaseImpl
import com.team.uptech.pomodoro.domain.interactor.impl.StartTimerUseCaseImpl
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.presenter.SettingsPresenter
import com.team.uptech.pomodoro.presentation.presenter.impl.MainPresenterImpl
import com.team.uptech.pomodoro.presentation.presenter.impl.SettingsPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created on 26.04.17.
 */
@Module
open class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun providePomodoroRepository(): PomodoroRepository = PomodoroRepositoryPrefsImpl(context)

    @Provides
    @Singleton
    fun provideStartTimerUseCase(pomodoroRepository: PomodoroRepository): StartTimerUseCase = StartTimerUseCaseImpl(pomodoroRepository)

    @Provides
    @Singleton
    fun provideMainPresenter(startTimerUseCase: StartTimerUseCase): MainPresenter = MainPresenterImpl(startTimerUseCase)

    @Provides
    @Singleton
    fun provideChangeTimeUseCase(pomodoroRepository: PomodoroRepository): ChangeTimeUseCase = ChangeTimeUseCaseImpl(pomodoroRepository)

    @Provides
    @Singleton
    fun provideSettingsPresenter(changeTimeUseCase: ChangeTimeUseCase): SettingsPresenter = SettingsPresenterImpl(changeTimeUseCase)
}