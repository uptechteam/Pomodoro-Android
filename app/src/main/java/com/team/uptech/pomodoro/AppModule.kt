package com.team.uptech.pomodoro

import android.content.Context
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import com.team.uptech.pomodoro.data.repository.impl.PomodoroRepositoryPrefsImpl
import com.team.uptech.pomodoro.domain.interactor.StartTimerUseCase
import com.team.uptech.pomodoro.domain.interactor.impl.StartTimerUseCaseImpl
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.presenter.impl.MainPresenterImpl
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
    fun provideMainPresenter(startTimerUseCase: StartTimerUseCase) : MainPresenter = MainPresenterImpl(startTimerUseCase)
}