package com.myblogtour.blogtour

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myblogtour.blogtour.databinding.ActivityMainBinding
import com.myblogtour.blogtour.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

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
                    true
                }
                R.id.btn_home_menu -> {
                    toFragment(HomeFragment())
                    true
                }
                R.id.btn_more_menu -> {
                    true
                }
                R.id.btn_profile_menu -> {
                    true
                }

                else ->
                    true

            }
        }
    }
}