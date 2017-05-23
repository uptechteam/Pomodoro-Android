package com.team.uptech.pomodoro

import com.team.uptech.pomodoro.presentation.ui.activity.MainActivity
import com.team.uptech.pomodoro.presentation.ui.fragment.SettingsFragment
import dagger.Subcomponent

/**
 * Created on 15.05.17.
 */

@PerActivity
@Subcomponent
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(settingsFragment: SettingsFragment)
}
