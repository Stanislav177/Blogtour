package com.myblogtour.blogtour.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.Fragment
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.appState.AppStateMainActivity
import com.myblogtour.blogtour.databinding.ActivityMainBinding
import com.myblogtour.blogtour.ui.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SYSTEM_THEME = 0
const val LIGHT_THEME = 1
const val NIGHT_THEME = 2

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTheme(getThemeSP())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            toFragment(HomeFragment())
        }
        initBottomAppBar()
    }

    private fun initBottomAppBar() {
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

    private fun startTheme(theme: Int) {
        return when (theme) {
            LIGHT_THEME -> {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
            NIGHT_THEME -> {
                setDefaultNightMode(MODE_NIGHT_YES)
            }
            SYSTEM_THEME -> {
                setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            }
            else -> {
                setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    private fun getThemeSP(): Int {
        val sharedPreferences = getSharedPreferences("KEY_SP_THEME", MODE_PRIVATE)
        return sharedPreferences.getInt("KEY_THEME", 0)
    }
}