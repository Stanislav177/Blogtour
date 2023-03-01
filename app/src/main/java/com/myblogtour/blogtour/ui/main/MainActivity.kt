package com.myblogtour.blogtour.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.appState.AppStateMainActivity
import com.myblogtour.blogtour.databinding.ActivityMainBinding
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment
import com.myblogtour.blogtour.ui.home.HomeFragment
import com.myblogtour.blogtour.ui.profile.ProfileFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            toFragment(HomeFragment())
            binding.navMenuBottom.selectedItemId = R.id.btn_home_menu
        }

        initBottomMenu()
    }

    private fun toFragment(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment, f).commit()
    }

    private fun initBottomMenu() {
        binding.navMenuBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btn_favorites_menu -> {
                    true
                }
                R.id.btn_profile_menu -> {
                    openFragmentUserProfileAuth()
                    true
                }
                R.id.btn_home_menu -> {
                    toFragment(HomeFragment())
                    true
                }
                R.id.btn_more_menu -> {
                    true
                }
                R.id.btn_search_menu -> {
                    true
                }
                else ->
                    true
            }
        }
    }

    private fun openFragmentUserProfileAuth() {
        viewModel.onRefresh()
        viewModel.getLiveData().observe(this) {
            when (it) {
                is AppStateMainActivity.CurrentUser -> {
                    if (it.currentUser) {
                        toFragment(ProfileFragment())
                    } else {
                        toFragment(AuthUserFragment())
                    }
                }
            }
        }
    }
}