package com.nagwa.library.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.nagwa.library.BR
import com.nagwa.library.util.bindView

/**
 * Created by MahmoudAyman on 7/17/2020.
 **/
abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    val baseProgress = ObservableBoolean()
    protected abstract val mViewModel: VM
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindView()
        binding.setVariable(BR.viewModel, mViewModel)
    }



}
