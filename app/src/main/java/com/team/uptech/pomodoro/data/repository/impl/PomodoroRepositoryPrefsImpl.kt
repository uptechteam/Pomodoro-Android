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
    private val TYPE1 = "WORK"
    private val TYPE2 = "BREAK"

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

    override fun getPomodoroType() = prefs.getString(TYPE, TYPE1)

    override fun getPomodoroTime(pomodoroType: String): Int {
        if (pomodoroType == TYPE1) {
            return prefs.getInt(pomodoroType, 10)
        } else {
            return prefs.getInt(pomodoroType, 5)
        }
    }

    override fun getIsInfinite() = prefs.getBoolean(IS_INFINITE, false)

    private fun getIsRunning() = prefs.getBoolean(IS_RUNNING, false)

    override fun savePomodoro(pomodoro: PomodoroData): Completable {
        return Completable.create { sb ->
            prefs.edit().putString(TYPE, pomodoro.type).apply()
            prefs.edit().putBoolean(IS_RUNNING, pomodoro.isRunning).apply()
            sb.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun setIsInfinite(infinite: Boolean): Completable {
        return Completable.create { sb ->
            prefs.edit().putBoolean(IS_INFINITE, infinite).apply()
            sb.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun saveTime(pomodoroType: String, time: Int): Completable {
        return Completable.create { sb ->
            prefs.edit().putInt(pomodoroType, time).apply()
            sb.onComplete()
        }.subscribeOn(Schedulers.io())
    }
}