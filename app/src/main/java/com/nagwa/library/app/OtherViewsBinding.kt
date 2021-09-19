package com.nagwa.library.app

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.nagwa.library.R
import com.nagwa.library.presentation.fragment.LibraryType
import com.nagwa.library.util.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber


class OtherViewsBinding {
    companion object {
        private const val IMAGE_TAG = "load_image"
    }

    @BindingAdapter("setProgress")
    fun startProgressBar(progressBar: ProgressBar, start: Boolean?) {

    }

    @BindingAdapter("setChipBgByType")
    fun setChipBg(chip: Chip, type: LibraryType) {
        val ctx = chip.context
        when (type) {
            LibraryType.PDF -> {
                chip.text = ctx.getString(R.string.pdf)
                chip.chipBackgroundColor =
                    AppUtil.getColorTintFromRes(chip.context, R.color.red)
            }
            else -> {
                chip.text = ctx.getString(R.string.video)
                chip.chipBackgroundColor =
                    AppUtil.getColorTintFromRes(chip.context, R.color.purple)
            }
        }
    }

    @BindingAdapter("loadGif")
    fun loadGifImage(imageView: ImageView, @DrawableRes imageRes: Int?) {
        imageRes?.let {
            Glide.with(imageView).asGif().load(imageRes).into(imageView)
        }
    }

    private fun loadEmptyImage(imageView: ImageView, progressBar: ProgressBar?) {
        imageView.loadImageFromURL(null, progressBar)
    }

    @BindingAdapter("app:visibleGone")
    fun bindViewGone(view: View, b: Boolean) {
        if (b) {
            view.visible()
        } else
            view.gone()
    }


    @BindingAdapter("adapter", "setHasFixedSize", requireAll = false)
    fun bindAdapter(
        recyclerView: RecyclerView,
        adapter: ListAdapter<*, *>?,
        hasFixedSize: Boolean?,
    ) {
        adapter?.let {
            recyclerView.adapter = it
            hasFixedSize?.let {
                recyclerView.setHasFixedSize(hasFixedSize)
            }
        } ?: Timber.e("adapter is null")
    }

}