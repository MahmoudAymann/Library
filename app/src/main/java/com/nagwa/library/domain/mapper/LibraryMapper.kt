package com.nagwa.library.domain.mapper

import com.nagwa.library.data.models.LibraryResponseItem
import com.nagwa.library.presentation.fragment.LibraryItem
import com.nagwa.library.presentation.fragment.LibraryType

object LibraryMapper {

    fun toLibItem(resList: List<LibraryResponseItem>): List<LibraryItem> {
        val finalList = arrayListOf<LibraryItem>()
        resList.forEach {
            finalList.add(LibraryItem(id = it.id,
                name = it.name,
                type = when (it.type) {
                    "VIDEO" -> LibraryType.VIDEO
                    else -> LibraryType.PDF
                },
                isDownloading = false,
                saved = false))
        }
        return finalList
    }

}