package com.nagwa.library.data

import android.content.Context
import com.nagwa.library.data.models.LibraryResponseItem
import com.nagwa.library.domain.mapper.LibraryMapper
import com.nagwa.library.presentation.fragment.LibraryItem
import com.nagwa.library.util.AppUtil.readJsonFromAssets
import com.nagwa.library.util.mMapToArrayListFix
import com.nagwa.library.util.mStringToJsonElement
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


//MARK: - Repo

class LibraryRepositoryRepository @Inject constructor (val context: Context) : LibraryRepositoryInterface
{
  //MARK:- read json from  Assets File

  override fun getListOfLibrary(): Observable<List<LibraryResponseItem>>
  {
    val json = readJsonFromAssets(context)
    //MARK:-
    val list = json?.mStringToJsonElement()?.asJsonArray?.mMapToArrayListFix<LibraryResponseItem>()?: arrayListOf()
    return Observable.create<List<LibraryResponseItem>> { sb ->
      //Transaction or network imitation
      Thread.sleep(1)
      sb.onNext(list)
    }.subscribeOn(Schedulers.io())
  }
}