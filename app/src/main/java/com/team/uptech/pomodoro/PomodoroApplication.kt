package com.team.uptech.pomodoro

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho


/**
 * Created on 26.04.17.
 */
class PomodoroApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()

        Stetho.initializeWithDefaults(this)

//        val crashlyticsKit = Crashlytics.Builder()
//                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
//                .build()
//        Fabric.with(applicationContext, crashlyticsKit)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}