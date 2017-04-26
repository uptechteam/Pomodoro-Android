package com.team.uptech.pomodoro

import dagger.Component
import javax.inject.Singleton

/**
 * Created on 26.04.17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: PomodoroApplication)
    fun inject(baseActivity: BaseActivity)
    fun inject(mainActivity: MainActivity)
}