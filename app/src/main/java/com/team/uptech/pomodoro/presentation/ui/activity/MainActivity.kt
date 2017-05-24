package com.team.uptech.pomodoro.presentation.ui.activity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Vibrator
import android.support.v4.app.NotificationCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.team.uptech.pomodoro.R
import com.team.uptech.pomodoro.presentation.model.Pomodoro
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.ui.view.MainView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textResource
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created on 26.04.17.
 */
class MainActivity : BaseActivity(), MainView {
    @Inject lateinit var presenter: MainPresenter

    private var notificationManager: NotificationManager? = null
    private var tickDisposable: Disposable? = null
    private val NOTIFICATION_ID = 0
    private var currentPomodoro: Pomodoro? = null

    override fun getContentView() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
        setSupportActionBar(toolbar)

        button_start_stop.setOnClickListener {
            presenter.onStartStopClicked()
        }
        presenter.bind(this)
    }

//    override fun onRetainCustomNonConfigurationInstance(): Any {
//        return presenter
//    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    override fun showError(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun showTimer(pomodoro: Pomodoro) {
        tickDisposable?.dispose()
        currentPomodoro = pomodoro
        currentPomodoro?.let {
            val maxValue = it.type.time

            val builder = generateNotificationBuilder(maxValue)

            textView.text = it.type.toString()
            button_start_stop.textResource = R.string.stop_timer

            timer_with_progress.visibility = View.GONE
            timer_with_progress.visibility = View.VISIBLE
            timer_with_progress.progress = timer_with_progress.maxProgress / maxValue
            tickDisposable = tick()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        builder.setProgress(maxValue, response, false)
                        notificationManager?.notify(NOTIFICATION_ID, builder.build())
                        timer_with_progress.progress = timer_with_progress.maxProgress / maxValue * (response.toFloat() + 1)
                    }, { error ->
                        showError(error.toString())
                    }, {
                        presenter.onTimerFinished()
                    })
        }
    }

    override fun hideTimer() {
        (getSystemService(VIBRATOR_SERVICE) as? Vibrator)?.vibrate(800)
        timer_with_progress.progress = 0f
        timer_with_progress.visibility = View.GONE
        notificationManager?.cancel(NOTIFICATION_ID)
        tickDisposable?.dispose()

        textView.textResource = R.string.not_work
        button_start_stop.textResource = R.string.start_timer
    }

    override fun showMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun generateNotificationBuilder(maxValue: Int): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.drawable.angry_pomodoro)
                .setProgress(maxValue, 0, false)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.angry_pomodoro))
                .setContentTitle("Work! Work! Work")
                .setContentText("You are working now!!!")

        val resultIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, System.currentTimeMillis().toInt(), resultIntent, 0)

        builder.setContentIntent(pendingIntent)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager?.notify(NOTIFICATION_ID, builder.build())
        return builder
    }

    private fun tick() = Observable.interval(1, TimeUnit.SECONDS)
            .take(currentPomodoro?.type?.time?.toLong() ?: 0)
            .map { it.toInt() + 1 } //start from first

    private fun openSettingsActivity() = startActivity<SettingsActivity>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_open_settings -> openSettingsActivity()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
