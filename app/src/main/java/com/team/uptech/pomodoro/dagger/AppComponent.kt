package com.team.uptech.pomodoro.dagger

import dagger.Component
import javax.inject.Singleton

/**
 * Created on 26.04.17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun activityComponent(): ActivityComponent
}