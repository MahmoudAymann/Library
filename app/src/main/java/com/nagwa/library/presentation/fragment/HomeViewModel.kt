package com.nagwa.library.presentation.fragment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.nagwa.library.base.AndroidBaseViewModel
import com.nagwa.library.domain.LibraryUseCase
import com.nagwa.library.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(app: Application, private val useCase: LibraryUseCase) :
    AndroidBaseViewModel(app) {

    private var resDis: Disposable ?= null
    val adapter =  LibraryAdapter(::onItemClick, ::openItemClick)
    val updateUiLiveData = MutableLiveData<Int>()

    private fun openItemClick(item: LibraryItem) {
        updateUiLiveData.value = Constants.OPEN
    }

    private fun onItemClick(item: LibraryItem) {
        adapter.startDownload(item)
    }

    fun pullJson() {
        resDis = useCase.getLibraryList().subscribe { a ->
            adapter.setList(a)
        }
    }

    override fun onCleared() {
        resDis?.dispose()
        super.onCleared()
    }
}