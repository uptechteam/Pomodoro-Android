package com.team.uptech.pomodoro.data.repository.impl

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.team.uptech.pomodoro.data.model.PomodoroData
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created on 28.04.17.
 */
class PomodoroRepositoryPrefsImpl @Inject constructor(context: Context) : PomodoroRepository {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val TYPE = "TYPE"
    private val IS_INFINITE = "IS_INFINITE"
    private val IS_RUNNING = "IS_RUNNING"

    override fun getPomodoro(): Single<PomodoroData> {
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return Single.create<PomodoroData> { subcriber ->
            val pomodoroType = getPomodoroType()
            val pomodoro = PomodoroData(pomodoroType, getPomodoroTime(pomodoroType), getIsRunning())
            subcriber.onSuccess(pomodoro)
        }.subscribeOn(Schedulers.io())
    }

    override fun getPomodoroType() = prefs.getString(TYPE, "WORK")

    override fun getPomodoroTime(pomodoroType: String) = prefs.getInt(pomodoroType, 0)

    override fun getIsInfinite() = prefs.getBoolean(IS_INFINITE, false)

    private fun getIsRunning() = prefs.getBoolean(IS_RUNNING, false)

    override fun savePomodoro(pomodoro: PomodoroData): Completable {
        return Completable.fromSingle<Boolean> { sb ->
            prefs.edit().putString(TYPE, pomodoro.type).apply()
            prefs.edit().putInt(pomodoro.type, pomodoro.time).apply()
            prefs.edit().putBoolean(IS_RUNNING, pomodoro.isRunning).apply()
            sb.onSuccess(true)
        }.subscribeOn(Schedulers.io())
    }

    override fun setIsInfinite(infinite: Boolean): Completable {
        return Completable.fromSingle<Boolean> { sb ->
            prefs.edit().putBoolean(IS_INFINITE, infinite).apply()
            sb.onSuccess(true)
        }.subscribeOn(Schedulers.io())
    }
}