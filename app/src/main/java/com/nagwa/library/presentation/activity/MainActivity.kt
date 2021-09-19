package com.nagwa.library.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nagwa.library.R
import com.nagwa.library.base.BaseActivity
import com.nagwa.library.databinding.ActivityMainBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val mViewModel: MainViewModel by viewModels()
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavController()
        mViewModel.apply {
            obsShowProgress = this@MainActivity.baseProgress
        }
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
    }


}