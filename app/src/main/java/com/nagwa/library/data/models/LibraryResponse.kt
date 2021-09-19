package com.nagwa.library.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
//MARK:Json Response
@Parcelize
data class LibraryResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable
