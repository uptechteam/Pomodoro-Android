package com.team.uptech.pomodoro.presentation.ui.fragment

import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.team.uptech.pomodoro.ActivityComponent
import com.team.uptech.pomodoro.R
import com.team.uptech.pomodoro.getAppComponent
import com.team.uptech.pomodoro.presentation.model.PomodoroType
import com.team.uptech.pomodoro.presentation.presenter.SettingsPresenter
import com.team.uptech.pomodoro.presentation.ui.SettingsView
import com.team.uptech.pomodoro.presentation.ui.activity.BaseActivity
import javax.inject.Inject


/**
 * Created on 27.04.17.
 */
class SettingsFragment : PreferenceFragment(), SettingsView {

    private var activityComponent: ActivityComponent? = null

    @Inject lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = activity.getAppComponent().activityComponent()
        activityComponent?.inject(this)

        addPreferencesFromResource(R.xml.settings)

        initPreferences()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this)
        presenter.getPomodoroTime(PomodoroType.WORK)
        presenter.getPomodoroTime(PomodoroType.BREAK)
        presenter.getIsInfinite()
    }

    override fun showWorkTime(workTime: Int) {
        val workTimePreference = findPreference("work_time") as EditTextPreference
        workTimePreference.summary = workTime.toString()
        workTimePreference.text = workTime.toString()
        workTimePreference.setOnPreferenceChangeListener { preference, time ->
            presenter.onWorkTimeChanged(Integer.valueOf(time.toString()))
            workTimePreference.summary = time.toString()
            true
        }
    }

    override fun showRelaxTime(relaxTime: Int) {
        val relaxTimePreference = findPreference("relax_time") as EditTextPreference
        relaxTimePreference.summary = relaxTime.toString()
        relaxTimePreference.text = relaxTime.toString()
        relaxTimePreference.setOnPreferenceChangeListener { preference, time ->
            presenter.onRelaxTimeChanged(Integer.valueOf(time.toString()))
            relaxTimePreference.summary = time.toString()
            true
        }
    }

    override fun showIsInfinite(isInfinite: Boolean) {
        val infiniteTimer = findPreference("infinite_timer") as SwitchPreference
        if (infiniteTimer.isChecked != isInfinite) {
            infiniteTimer.isChecked = isInfinite
        }
        infiniteTimer.setOnPreferenceChangeListener { preference, isChecked ->
            presenter.onInfinityChanged(isChecked as Boolean)
            true
        }
    }

    override fun showDialog(messageId: Int, shouldCloseScreen: Boolean) {
        if ((activity as BaseActivity).isAlive()) {
            AlertDialog.Builder(activity).setMessage(messageId)
                    .setPositiveButton(android.R.string.ok, { dialog, _ ->
                        dialog.dismiss()
                        if (shouldCloseScreen) {
                            activity.onBackPressed()
                        }
                    }).create().show()
        }
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showError(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

    fun initPreferences() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind()
    }

    override fun onDestroy() {
        activityComponent = null
        super.onDestroy()
    }
}