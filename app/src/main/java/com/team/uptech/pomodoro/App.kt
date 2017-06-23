package com.team.uptech.pomodoro

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.facebook.stetho.Stetho
import com.team.uptech.pomodoro.dagger.AppComponent
import com.team.uptech.pomodoro.dagger.AppModule
import com.team.uptech.pomodoro.dagger.DaggerAppComponent
import io.fabric.sdk.android.Fabric


/**
 * Created on 26.04.17.
 */
class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()

        Stetho.initializeWithDefaults(this)

        val crashlyticsKit = Crashlytics.Builder()
                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build()
        Fabric.with(applicationContext, crashlyticsKit)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}