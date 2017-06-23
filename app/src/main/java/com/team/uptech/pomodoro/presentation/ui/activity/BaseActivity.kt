package com.team.uptech.pomodoro.presentation.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.team.uptech.pomodoro.dagger.ActivityComponent
import com.team.uptech.pomodoro.utils.getAppComponent

/**
 * Created on 26.04.17.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = BaseActivity::class.java.simpleName
    protected var activityComponent: ActivityComponent? = null

    protected abstract fun getContentView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        activityComponent = getAppComponent().activityComponent()
    }

    fun isAlive(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !isDestroyed && !isFinishing
        } else {
            return !isFinishing
        }
    }

    override fun onDestroy() {
        activityComponent = null
        super.onDestroy()
    }
}