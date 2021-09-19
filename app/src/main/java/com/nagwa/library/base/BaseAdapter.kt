package com.nagwa.library.base

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagwa.library.BR
import timber.log.Timber

abstract class BaseAdapter<T : Parcelable, B : ViewDataBinding>(
    diffUtilCallback:DiffUtil.ItemCallback<T>,
    private val itemClick: (T) -> Unit = {},
) : ListAdapter<T, BaseViewHolder<T>>(diffUtilCallback) {

    private val mListOfItems = arrayListOf<T?>()

     var mPosition: Int = 0
    lateinit var binding: B

    @LayoutRes
    abstract fun layoutRes(): Int
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes(),
            parent,
            false
        )
        return BaseViewHolder<T>(binding).apply {
            onViewHolderCreated(this)
            itemView.setOnClickListener {
                this@BaseAdapter.mPosition = adapterPosition
                itemClick(getItem(adapterPosition))
            }
        }
    }

    abstract fun onViewHolderCreated(baseViewHolder: BaseViewHolder<T>)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    fun removeItem(item: T?, isListEmpty: (Boolean) -> Unit = {}) {
        Timber.e("deleting > $item in pos: $mPosition")
        item?.let {
            mListOfItems.remove(it)
            submitList(mListOfItems.toMutableList())
            isListEmpty(mListOfItems.size == 0)
        }
    }

    fun updateList(newList: List<T>) {
        mListOfItems.addAll(newList)
        submitList(mListOfItems.toMutableList())
    }

    fun setList(newList: List<T?>?) {
        mListOfItems.clear()
        mListOfItems.addAll(newList ?: emptyList())
        submitList(mListOfItems.toMutableList())
    }

    fun addItemToList(item: T?, isAdded: (Boolean) -> Unit) {
        item?.let {
            mListOfItems.add(item)
            submitList(mListOfItems.toMutableList())
            isAdded(true)
        } ?: Timber.e("item is null")
    }

    fun updateItem(item: T?) {
        item?.let {
            mListOfItems[mPosition] = item
            submitList(mListOfItems)
            notifyItemChanged(mPosition)
        } ?: Timber.e("item is null")
    }

    fun clearCurrentList() {
        mListOfItems.clear()
        submitList(mListOfItems.toMutableList())
    }

}

class BaseViewHolder<T>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: T) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}


