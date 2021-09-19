package com.nagwa.library.data

import com.nagwa.library.data.models.LibraryResponseItem
import io.reactivex.rxjava3.core.Observable


interface LibraryRepositoryInterface {
  fun getListOfLibrary(): Observable<List<LibraryResponseItem>>
}