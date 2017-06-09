package com.team.uptech.pomodoro.data.repository.impl

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.team.uptech.pomodoro.data.model.PomodoroType
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

    override fun getCurrentPomodoro(): Single<PomodoroType?> {
        return Single.create<PomodoroType?> { sb ->
            val pomodoroType = getPomodoroType()
            if (pomodoroType == "") {
                sb.onSuccess(null)
            }
            val pomodoro = PomodoroType.valueOf(pomodoroType)
            getPomodoroTypeTime(pomodoro).subscribe({
                pomodoro.time = it
                sb.onSuccess(pomodoro)
            }, {
                Log.e("PomodoroRepositoryPrefs", "", it)
            })
        }.subscribeOn(Schedulers.io())
                .delay(1.toLong(), TimeUnit.SECONDS) //Just for testing =)
    }

    private fun getPomodoroType() = prefs.getString(CURRENT_TYPE, TYPE_WORK)

    override fun getIsInfiniteMode(): Single<Boolean> {
        return Single.create<Boolean> {
            val result = prefs.getBoolean(IS_INFINITE, false)
            it.onSuccess(result)
        }.subscribeOn(Schedulers.io())
    }

    override fun getPomodoroTypeTime(pomodoro: PomodoroType): Single<Int> {
        return Single.create<Int> {
            if (pomodoro.name == TYPE_WORK) {
                it.onSuccess(prefs.getInt(pomodoro.name, DEFAULT_WORK_TIME))
            } else {
                it.onSuccess(prefs.getInt(pomodoro.name, DEFAULT_BREAK_TIME))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun saveCurrentPomodoro(pomodoro: PomodoroType?): Completable {
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

    override fun savePomodoroTypeTime(pomodoro: PomodoroType, time: Int): Completable {
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
        private val DEFAULT_WORK_TIME = 10 //seconds (for testing)
        private val DEFAULT_BREAK_TIME = 5
    }
}