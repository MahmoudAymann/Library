package com.nagwa.library.util

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.nagwa.library.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

object AppUtil {

    fun readJsonFromAssets(context: Context) : String? {
        val jsonString: String
        val fileName = "getListOfFilesResponse.json"
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getColorTintFromRes(context: Context, @ColorRes colorRes: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, colorRes)
    }

    fun repeatTask(stopPoint: Long, stepCallBack: (Long) -> Unit, onComplete: () -> Unit) {
        var count: Long = 0
        Observable.timer(count, TimeUnit.MILLISECONDS)
            .delay(1, TimeUnit.SECONDS)
            .repeatUntil { count >= stopPoint }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable?) { }
                override fun onNext(t: Long?) {
                    count += 5
                    if (count < 100) //to make sure its only for progress bar
                        stepCallBack.invoke(count)
                }

                override fun onComplete() {
                    onComplete.invoke()
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }
            })
    }

}