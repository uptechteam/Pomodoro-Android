package com.team.uptech.pomodoro

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Vibrator
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import com.team.uptech.pomodoro.domain.interactor.TimerUseCase
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.ui.activity.MainActivity
import com.team.uptech.pomodoro.utils.getAppComponent
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created on 24.05.17.
 */
class TimerService : Service() {

    @Inject lateinit var timer: TimerUseCase
    @Inject lateinit var presenter: MainPresenter

    private var tickDisposable: Disposable? = null

    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        getAppComponent().activityComponent().inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "TimerService started!", Toast.LENGTH_SHORT).show()
        val timerTime = intent?.getIntExtra(timerTime, 0) ?: 0
        val removeNotification = intent?.getIntExtra("StopService", 0) ?: 0
        if (removeNotification == REMOVE_NOTIFICATION_ID) {
            stopSelf()
        } else {
            notificationBuilder = generateProgressBuilder(timerTime)
            startForeground(NOTIFICATION_ID, notificationBuilder.build())

            startTimer(timerTime)
        }
        return START_STICKY
    }

    private fun startTimer(timerTime: Int) {
        timer.startTimer(timerTime)

        tickDisposable = timer.getTimerSubject()
                ?.subscribe({ sb ->
                    Log.d("LOOOL", "sb = " + sb)
                    notificationBuilder.setProgress(timerTime, sb, false)
                    startForeground(NOTIFICATION_ID, notificationBuilder.build())
                }, { error ->
                    Log.e("TimerService", "", error)
                }, {
                    timer.setTimerSubject(PublishSubject.create())
                    presenter.onTimerFinished()
                    (getSystemService(VIBRATOR_SERVICE) as? Vibrator)?.vibrate(400)
                    startForeground(NOTIFICATION_ID, generateDoneBuilder().build())
                    stopForeground(false)
                })
    }

    private fun generateProgressBuilder(maxValue: Int): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setProgress(maxValue, 0, false)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.angry_pomodoro))
                .setContentTitle("Work! Work! Work")
                .setContentText("You are working now!!!")

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
        tickDisposable?.dispose()
        timer.stopTimer()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d("LOOOL", "Service onTaskRemoved()")
        super.onTaskRemoved(rootIntent)
    }

    override fun onBind(p0: Intent?) = null

    companion object {
        val timerTime = "TimerTime"
        private val NOTIFICATION_ID = 800
        private val REMOVE_NOTIFICATION_ID = 1007
    }
}