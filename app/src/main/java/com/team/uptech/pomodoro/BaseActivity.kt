package com.team.uptech.pomodoro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.os.Build

/**
 * Created on 26.04.17.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = BaseActivity::class.java.simpleName

    protected abstract fun getContentView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        PomodoroApplication.component.inject(this)
    }

    fun isAlive(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !isDestroyed && !isFinishing
        } else {
            return !isFinishing
        }
    }
}