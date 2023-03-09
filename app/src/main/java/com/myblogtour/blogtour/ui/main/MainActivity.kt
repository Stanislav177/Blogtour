package com.myblogtour.blogtour.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.appState.AppStateMainActivity
import com.myblogtour.blogtour.databinding.ActivityMainBinding
import com.myblogtour.blogtour.ui.home.HomeFragment
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
        }
        initAppBar()
    }

    private fun initAppBar() {
        with(binding) {
            btnHomeFab.setOnClickListener {
                toFragment(HomeFragment())
            }
            appBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.btn_search_menu -> {
                        Toast.makeText(this@MainActivity, "Поиск", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            appBar.setNavigationOnClickListener {
                viewModel.onRefresh()
                viewModel.getLiveData().observe(this@MainActivity) {
                    when (it) {
                        is AppStateMainActivity.CurrentUser -> {
                            BottomNavigationDrawerFragment().show(
                                this@MainActivity.supportFragmentManager,
                                "ff"
                            )
                        }
                        is AppStateMainActivity.NoCurrentUser -> {
                            BottomNavigationDrawerFragmentAuth().show(
                                this@MainActivity.supportFragmentManager,
                                "f"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun toFragment(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment, f).commit()
    }
}