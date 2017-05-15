package com.team.uptech.pomodoro.presentation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.team.uptech.pomodoro.R
import com.team.uptech.pomodoro.presentation.presenter.MainPresenter
import com.team.uptech.pomodoro.presentation.ui.PomodoroView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textResource
import javax.inject.Inject


/**
 * Created on 26.04.17.
 */
class MainActivity : BaseActivity(), PomodoroView {
    @Inject lateinit var presenter: MainPresenter

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

    override fun showTimer() {
        textView.textResource = R.string.work
        progress_bar.visibility = View.VISIBLE
        button_start_stop.textResource = R.string.stop_timer
    }

    override fun hideTimer() {
        textView.textResource = R.string.not_work
        progress_bar.visibility = View.GONE
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
