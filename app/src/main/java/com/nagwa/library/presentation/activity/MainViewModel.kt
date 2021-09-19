package com.nagwa.library.presentation.activity

import android.app.Application
import androidx.databinding.ObservableBoolean
import com.nagwa.library.base.AndroidBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (app: Application) : AndroidBaseViewModel(app) {
    var obsShowProgress = ObservableBoolean()

}