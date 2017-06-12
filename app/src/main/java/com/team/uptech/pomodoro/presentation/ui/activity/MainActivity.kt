package com.team.uptech.pomodoro.presentation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.team.uptech.pomodoro.R
import com.team.uptech.pomodoro.data.model.Pomodoro
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.ui.ProgressListener
import com.team.uptech.pomodoro.presentation.ui.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textResource
import javax.inject.Inject


/**
 * Created on 26.04.17.
 */
class MainActivity : BaseActivity(), MainView, ProgressListener {

    @Inject lateinit var presenter: MainPresenter

    override fun getContentView() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
        setSupportActionBar(toolbar)

        button_start_stop.setOnClickListener {
            if (button_start_stop.text == getString(R.string.start_timer)) {
                presenter.onStartClicked()
            } else {
                presenter.onStopClicked()
            }
        }
        presenter.bind(this)
        presenter.showCurrentState()
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
    }

    override fun updateTimerProgress(value: Int, maxValue: Int) {
        timer_with_progress.let {
            if (it.visibility == View.GONE) {
                it.visibility = View.VISIBLE
            }
            it.maxProgress = maxValue.toFloat()
            it.progress = value.toFloat()
        }
    }

    override fun timerFinished() {
        hideTimer()
    }

    override fun showCurrentState(pomodoro: Pomodoro) {
        if (pomodoro != Pomodoro.NOT_WORKING) {
            textView.text = pomodoro.name
            button_start_stop.textResource =
                    if (pomodoro == Pomodoro.WORK || pomodoro == Pomodoro.BREAK) R.string.stop_timer
                    else R.string.start_timer
        }
    }

    override fun showTimer(pomodoro: Pomodoro) {
        textView.text = pomodoro.name
        button_start_stop.textResource = R.string.stop_timer
    }

    override fun hideTimer() {
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
