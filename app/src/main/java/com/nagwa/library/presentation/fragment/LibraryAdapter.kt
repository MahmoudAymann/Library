package com.nagwa.library.presentation.fragment

import androidx.recyclerview.widget.DiffUtil
import com.nagwa.library.R
import com.nagwa.library.base.BaseAdapter
import com.nagwa.library.base.BaseViewHolder
import com.nagwa.library.databinding.ItemLibViewBinding
import com.nagwa.library.util.AppUtil
import timber.log.Timber

class LibraryAdapter(itemClick: (LibraryItem) -> Unit,
                     val openClick: (LibraryItem) -> Unit) :
    BaseAdapter<LibraryItem, ItemLibViewBinding>(ItemDiffCallback(),itemClick) {
    override fun layoutRes(): Int = R.layout.item_lib_view




    fun startDownload(item: LibraryItem) {
        Timber.e("start")
        item.isDownloading = true
        updateItem(item)
        AppUtil.repeatTask(100, {
            binding.pb.progress = it.toInt()
        }){
            binding.pb.progress = 100
            downloadComplete(item)
        }
    }

    private fun downloadComplete(item: LibraryItem) {
        item.isDownloading = false
        item.saved = true
        updateItem(item)
    }


    class ItemDiffCallback : DiffUtil.ItemCallback<LibraryItem>() {
        override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem) =
            oldItem.equals(newItem)
    }

    override fun onViewHolderCreated(baseViewHolder: BaseViewHolder<LibraryItem>) {
        binding.fabOpen.setOnClickListener {
            mPosition = baseViewHolder.adapterPosition
            openClick.invoke(getItem(mPosition))
        }
    }

}