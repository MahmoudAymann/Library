package com.nagwa.library.domain

import com.nagwa.library.data.LibraryRepositoryInterface
import com.nagwa.library.domain.mapper.LibraryMapper
import com.nagwa.library.presentation.fragment.LibraryItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibraryUseCase @Inject constructor(private val libraryRepository: LibraryRepositoryInterface) {

    fun getLibraryList(): Observable<List<LibraryItem>> {
        return libraryRepository.getListOfLibrary()
            .map {
                LibraryMapper.toLibItem(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}
