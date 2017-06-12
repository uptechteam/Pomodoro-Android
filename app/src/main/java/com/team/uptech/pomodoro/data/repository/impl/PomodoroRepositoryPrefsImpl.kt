package com.team.uptech.pomodoro.data.repository.impl

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.team.uptech.pomodoro.data.model.Pomodoro
import com.team.uptech.pomodoro.data.repository.PomodoroRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created on 28.04.17.
 */
class PomodoroRepositoryPrefsImpl @Inject constructor(context: Context) : PomodoroRepository {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getCurrentPomodoro(): Single<Pomodoro> {
        return Single.create<Pomodoro> { sb ->
            val pomodoroTypeString = getPomodoroType()
            val pomodoro = Pomodoro.valueOf(pomodoroTypeString)
            getPomodoroTypeTime(pomodoro)
                    .subscribe({
                        pomodoro.time = it
                        sb.onSuccess(pomodoro)
                    }, {
                        sb.onError(it)
                        Log.e("PomodoroRepositoryPrefs", "", it)
                    })
        }.subscribeOn(Schedulers.io())
                .delay(1.toLong(), TimeUnit.SECONDS) //Just for testing =)
    }

    private fun getPomodoroType() = prefs.getString(CURRENT_TYPE, TYPE_NOT_WORKING)

    override fun getIsInfiniteMode(): Single<Boolean> {
        return Single.create<Boolean> {
            val result = prefs.getBoolean(IS_INFINITE, false)
            it.onSuccess(result)
        }.subscribeOn(Schedulers.io())
    }

    override fun getPomodoroTypeTime(pomodoro: Pomodoro): Single<Int> {
        return Single.create<Int> {
            when (pomodoro) {
                Pomodoro.WORK -> it.onSuccess(prefs.getInt(pomodoro.name, DEFAULT_WORK_TIME))
                Pomodoro.BREAK -> it.onSuccess(prefs.getInt(pomodoro.name, DEFAULT_BREAK_TIME))
                Pomodoro.NOT_WORKING -> it.onSuccess(prefs.getInt(pomodoro.name, 0))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun saveCurrentPomodoro(pomodoro: Pomodoro?): Completable {
        return Completable.create {
            if (pomodoro != null) {
                prefs.edit().putString(CURRENT_TYPE, pomodoro.name).apply()
            } else {
                prefs.edit().putString(CURRENT_TYPE, "").apply()
            }
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun saveIsInfiniteMode(infinite: Boolean): Completable {
        return Completable.create {
            prefs.edit().putBoolean(IS_INFINITE, infinite).apply()
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun savePomodoroTypeTime(pomodoro: Pomodoro, time: Int): Completable {
        return Completable.create {
            prefs.edit().putInt(pomodoro.name, time).apply()
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    companion object {
        private val CURRENT_TYPE = "CURRENT_TYPE"
        private val IS_INFINITE = "IS_INFINITE"
        private val TYPE_WORK = "WORK"
        private val TYPE_BREAK = "BREAK"
        private val TYPE_NOT_WORKING = "NOT_WORKING"
        private val DEFAULT_WORK_TIME = 10 //seconds (for testing)
        private val DEFAULT_BREAK_TIME = 5
    }
}