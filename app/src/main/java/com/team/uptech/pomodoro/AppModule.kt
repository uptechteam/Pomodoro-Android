package com.team.uptech.pomodoro

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created on 26.04.17.
 */
@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun providePrefs(): AppPreferences {
        return AppPreferences(context)
    }
}