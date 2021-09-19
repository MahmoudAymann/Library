package com.nagwa.library.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.nagwa.library.util.bindView
import com.nagwa.library.BR
import com.nagwa.library.util.castToActivity

/**
 * Created by MahmoudAyman on 6/17/2020.
 **/

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel> :
    Fragment() {

    protected abstract val mViewModel: VM
    protected abstract fun onCreateView(savedInstanceState: Bundle?)
    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = bindView()
        binding.setVariable(BR.viewModel, mViewModel)
        onCreateView(savedInstanceState)
        return binding.root
    }

    fun closeFragment() {
        activity?.onBackPressed()
    }

    fun showProgress(show: Boolean = true) {
        castToActivity<BaseActivity<*, *>> {
            it?.baseProgress?.set(show)
        }
    }

    fun getOnBackClick(callBack: (OnBackPressedCallback) -> Unit) {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callBack(this)
                    isEnabled
                }
            })
    }

    override fun onPause() {
        super.onPause()
        showProgress(false)
    }
}
