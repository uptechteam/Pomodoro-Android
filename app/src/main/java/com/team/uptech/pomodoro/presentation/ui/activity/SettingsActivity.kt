package com.team.uptech.pomodoro.presentation.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.team.uptech.pomodoro.R
import com.team.uptech.pomodoro.presentation.ui.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created on 26.04.17.
 */
class SettingsActivity : BaseActivity() {

    override fun getContentView() = R.layout.activity_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        fragmentManager.beginTransaction().replace(R.id.settings_container, SettingsFragment()).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
