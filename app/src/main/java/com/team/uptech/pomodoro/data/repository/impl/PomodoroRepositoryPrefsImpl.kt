package com.team.uptech.pomodoro.data.repository.impl

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.team.uptech.pomodoro.data.model.PomodoroData
import com.team.uptech.pomodoro.data.model.PomodoroTypeData
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created on 28.04.17.
 */
class PomodoroRepositoryPrefsImpl @Inject constructor(context: Context) : PomodoroRepository {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val IS_RUNNING = "IS_RUNNING"
    private val TYPE = "TYPE"

    override fun getPomodoro(): Observable<PomodoroData> {
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return Observable.create<PomodoroData> { subcriber ->
            val pomodoro = PomodoroData(getPomodoroIsRunning(), getPomodoroType())
            subcriber.onNext(pomodoro)
            subcriber.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun getPomodoroType(): PomodoroTypeData {
        return PomodoroTypeData.valueOf(prefs.getString(TYPE, "WORK"))
    }

    override fun getPomodoroIsRunning(): Boolean {
        return prefs.getBoolean(IS_RUNNING, true)
    }

    override fun getPomodoroTime(): Int {
        return 0//for future
    }

    override fun savePomodoro(pomodoro: PomodoroData): Completable {
        return Completable.fromSingle<Boolean> { sb ->
            prefs.edit().putBoolean(IS_RUNNING, pomodoro.isRunning).apply()
            prefs.edit().putString(TYPE, pomodoro.type.toString()).apply()
            sb.onSuccess(true)
        }.subscribeOn(Schedulers.io())
    }
}