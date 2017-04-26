package com.team.uptech.pomodoro

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

/**
 * Created on 26.04.17.
 */
class MainActivity : BaseActivity() {

    override fun getContentView() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PomodoroApplication.component.inject(this)
        setSupportActionBar(toolbar)
    }

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

    private fun openSettingsActivity() {
        startActivity<SettingsActivity>()
    }
}
