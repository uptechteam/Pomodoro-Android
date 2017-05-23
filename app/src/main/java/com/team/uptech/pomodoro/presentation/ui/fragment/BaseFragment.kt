package com.team.uptech.pomodoro.presentation.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.uptech.pomodoro.ActivityComponent
import com.team.uptech.pomodoro.getAppComponent

/**
 * Created on 18.05.17.
 */
abstract class BaseFragment: Fragment() {

    protected val TAG = BaseFragment::class.java.simpleName
    protected var activityComponent: ActivityComponent? = null

    protected abstract fun getContentView(): Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getContentView(), container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityComponent = context.getAppComponent().activityComponent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activityComponent = null
    }
}