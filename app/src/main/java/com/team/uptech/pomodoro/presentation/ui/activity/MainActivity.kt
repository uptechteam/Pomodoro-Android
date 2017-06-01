package com.team.uptech.pomodoro.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.team.uptech.pomodoro.R
import com.team.uptech.pomodoro.TimerService
import com.team.uptech.pomodoro.domain.interactor.TimerUseCase
import com.team.uptech.pomodoro.presentation.model.Pomodoro
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.ui.view.MainView
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textResource
import javax.inject.Inject


/**
 * Created on 26.04.17.
 */
class MainActivity : BaseActivity(), MainView {

    @Inject lateinit var presenter: MainPresenter
    @Inject lateinit var timer: TimerUseCase
    private var tickDisposable: Disposable? = null

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

    override fun onResume() {
        super.onResume()
        presenter.getCurrentPomodoro()
    }

    override fun showCurrentState(pomodoro: Pomodoro) {
        textView.text = pomodoro.type.toString()
        button_start_stop.textResource = R.string.stop_timer

//        timer_with_progress.visibility = View.GONE
        timer_with_progress.visibility = View.VISIBLE
        timer_with_progress.progress = timer_with_progress.maxProgress / pomodoro.type.time
        tickDisposable = timer.getTimerSubject()
                ?.subscribe({ sb ->
                    Log.d("LOOOL", "MainActivity sb = " + sb)
                    timer_with_progress.progress = timer_with_progress.maxProgress / pomodoro.type.time * (sb.toFloat() + 1)
                }, { error ->
                    Log.d("MainActivity", "", error)
                }, {
                    timer.setTimerSubject(PublishSubject.create())
                    Log.d("LOOOL", "MainActivity getTimerSubject onComplete")
                })
    }

    override fun onPause() {
        tickDisposable?.dispose()
        super.onPause()
    }

    override fun showTimer(pomodoro: Pomodoro) {
        val serviceIntent = Intent(this, TimerService::class.java)
        serviceIntent.putExtra(TimerService.timerTime, pomodoro.type.time)
        startService(serviceIntent)
        textView.text = pomodoro.type.toString()
        button_start_stop.textResource = R.string.stop_timer

        timer_with_progress.visibility = View.GONE
        timer_with_progress.visibility = View.VISIBLE
        timer_with_progress.progress = timer_with_progress.maxProgress / pomodoro.type.time
        tickDisposable = timer.getTimerSubject()
                ?.subscribe({ sb ->
                    Log.d("LOOOL", "MainActivity sb = " + sb)
                    timer_with_progress.progress = timer_with_progress.maxProgress / (pomodoro.type.time) * (sb.toFloat() + 1)
                }, { error ->
                    Log.d("MainActivity", "", error)
                }, {
                    timer.setTimerSubject(PublishSubject.create())
                    Log.d("LOOOL", "MainActivity getTimerSubject onComplete")
                })
    }

    override fun hideTimer() {
        stopService(Intent(this, TimerService::class.java))
//        (getSystemService(VIBRATOR_SERVICE) as? Vibrator)?.vibrate(800)
        timer_with_progress.progress = 0f
        timer_with_progress.visibility = View.GONE
        textView.textResource = R.string.not_work
        button_start_stop.textResource = R.string.start_timer
    }

    override fun showMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

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
