package com.team.uptech.pomodoro.dagger

import com.team.uptech.pomodoro.presentation.service.TimerService
import com.team.uptech.pomodoro.dagger.scope.PerActivity
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
    fun inject(timerService: TimerService)
}
