package com.team.uptech.pomodoro

import com.team.uptech.pomodoro.presentation.ui.activity.MainActivity
import dagger.Subcomponent

/**
 * Created on 15.05.17.
 */

@PerActivity
@Subcomponent
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}
