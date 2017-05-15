package com.team.uptech.pomodoro

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