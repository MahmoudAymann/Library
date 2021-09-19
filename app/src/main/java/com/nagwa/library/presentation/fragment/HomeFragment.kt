package com.nagwa.library.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.nagwa.library.base.BaseFragment
import com.nagwa.library.databinding.FragmentHomeBinding
import com.nagwa.library.util.Constants
import com.nagwa.library.util.observe
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val mViewModel: HomeViewModel by viewModels()

    override fun onCreateView(savedInstanceState: Bundle?) {
        mViewModel.apply {
            observe(updateUiLiveData){
                when(it){
                    Constants.OPEN-> Toast.makeText(requireContext(), "open click", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.pullJson()
    }

}