package com.team.uptech.pomodoro.presentation.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Vibrator
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import com.team.uptech.pomodoro.R
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.presenter.TimerPresenter
import com.team.uptech.pomodoro.presentation.ui.activity.MainActivity
import com.team.uptech.pomodoro.utils.getAppComponent
import javax.inject.Inject

/**
 * Created on 24.05.17.
 */
class TimerService : Service() {

    @Inject lateinit var timerPresenter: TimerPresenter
    @Inject lateinit var presenter: MainPresenter // start timer from activity, finish from service

    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        getAppComponent().activityComponent().inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "TimerService started!", Toast.LENGTH_SHORT).show()
        val timerTime = intent?.getIntExtra(timerTime, 0) ?: 0
        val timerType = intent?.getStringExtra(timerType) ?: ""
        val removeNotification = intent?.getIntExtra("StopService", 0) ?: 0
        if (removeNotification == REMOVE_NOTIFICATION_ID) {
            stopSelf()
        } else {
            notificationBuilder = generateProgressBuilder(timerTime, timerType)
            startForeground(NOTIFICATION_ID, notificationBuilder.build())
            startTimer(timerTime)
        }
        return START_STICKY
    }

    private fun startTimer(timerTime: Int) {
        timerPresenter.onStartTimerClicked(timerTime)
        timerPresenter.getCurrentProgress()
                ?.subscribe({
                    notificationBuilder.setProgress(timerTime, it, false)
                    startForeground(NOTIFICATION_ID, notificationBuilder.build())
                }, {
                    Log.e("TimerService", "", it)
                }, {
                    presenter.onTimerFinished()
                    (getSystemService(VIBRATOR_SERVICE) as? Vibrator)?.vibrate(200)
                    startForeground(NOTIFICATION_ID, generateDoneBuilder().build())
                    stopForeground(false)
                })
    }

    private fun generateProgressBuilder(maxValue: Int, type: String): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setProgress(maxValue, 1, false)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.angry_pomodoro))
                .setContentTitle(type)
                .setContentText("This is a $type now!!!")

        val resultIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, System.currentTimeMillis().toInt(), resultIntent, 0)
        builder.setContentIntent(pendingIntent)
        return builder
    }

    private fun generateDoneBuilder(): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.angry_pomodoro))
                .setContentTitle("^_^")
                .setContentText("Well done!")

        val deleteIntent = Intent(applicationContext, TimerService::class.java)
        deleteIntent.putExtra("StopService", REMOVE_NOTIFICATION_ID)
        val pendingDeleteIntent = PendingIntent.getService(applicationContext, REMOVE_NOTIFICATION_ID,
                deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val resultIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingResultIntent = PendingIntent.getActivity(applicationContext,
                System.currentTimeMillis().toInt(), resultIntent, 0)

        builder.setDeleteIntent(pendingDeleteIntent)
        builder.setContentIntent(pendingResultIntent)
        return builder
    }

    override fun onDestroy() {
        Toast.makeText(this, "TimerService stopped!", Toast.LENGTH_SHORT).show()
        timerPresenter.onStopTimerClicked()
        super.onDestroy()
    }

    override fun onBind(p0: Intent?) = null

    companion object {
        val timerTime = "TimerTime"
        val timerType = "TimerType"
        private val NOTIFICATION_ID = 800
        private val REMOVE_NOTIFICATION_ID = 1007
    }
}