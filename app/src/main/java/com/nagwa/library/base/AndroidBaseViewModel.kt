package com.nagwa.library.base

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nagwa.library.util.Resource

open class AndroidBaseViewModel(val app: Application) : AndroidViewModel(app), Observable {
    private val mCallBacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallBacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallBacks.add(callback)
    }

    fun notifyChange() {
        mCallBacks.notifyChange(this, 0)
    }

    fun notifyChange(propertyId: Int) {
        mCallBacks.notifyChange(this, propertyId)
    }

}