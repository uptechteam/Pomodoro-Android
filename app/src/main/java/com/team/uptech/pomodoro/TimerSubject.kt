package com.team.uptech.pomodoro

import android.content.Context
import android.util.Log
import com.team.uptech.pomodoro.utils.getAppComponent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created on 25.05.17.
 */

class TimerSubject(val context: Context) {
    var timerSubject: PublishSubject<Int>? = PublishSubject.create()

    private var tickDisposable: Disposable? = null

    init {
        context.getAppComponent().inject(this)
    }

    fun startTimer(timerTime: Int) {
        tickDisposable?.dispose()
        tickDisposable = tick(timerTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    timerSubject?.onNext(response)
                }, { error ->
                    timerSubject?.onError(error)
                    Log.e("TimerService", "", error)
                    Log.d("LOOL", "error = " + error.toString())
                }, {
                    timerSubject?.onComplete()
                    Log.e("LOOL", "TimerSubject onComplete()")
                })
    }

    fun stopTimer() {
//        timerSubject = null
        tickDisposable?.dispose()
    }

    private fun tick(timerTime: Int) = Observable.interval(1, TimeUnit.SECONDS)
            .take(timerTime.toLong())
            .map { it.toInt() + 1 } //start from first
}
