package com.nagwa.library.presentation.fragment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LibraryItem(
    val id: Int? = null,
    var name: String? = null,
    val type: LibraryType? = null,
    var isDownloading: Boolean? = null,
    var saved: Boolean? = null,
) : Parcelable


enum class LibraryType {
    PDF,
    VIDEO
}
