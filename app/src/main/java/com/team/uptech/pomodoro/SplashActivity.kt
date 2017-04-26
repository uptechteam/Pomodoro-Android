package com.team.uptech.pomodoro

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

/**
 * Created on 26.04.17.
 */
class SplashActivity : AppCompatActivity() {

    private val DELAY_TIME = TimeUnit.SECONDS.toMillis(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity<MainActivity>()
            finish()
        }, DELAY_TIME)
    }
}
