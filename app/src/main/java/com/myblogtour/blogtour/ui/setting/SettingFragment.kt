package com.myblogtour.blogtour.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.persistableBundleOf
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.myblogtour.blogtour.databinding.FragmentSettingBinding
import com.myblogtour.blogtour.ui.main.MainActivity
import com.myblogtour.blogtour.utils.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    private lateinit var tabBtn: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabBtn = binding.tabGroup

        tabBtn.addTab(tabBtn.newTab().setText("Системная"))
        tabBtn.addTab(tabBtn.newTab().setText("Светлая"))
        tabBtn.addTab(tabBtn.newTab().setText("Темная"))

        tabBtn.selectTab(binding.tabGroup.getTabAt(getThemeApp()))

        tabBtn.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        setThemeApp(tab.position)
                    }
                    1 -> {
                        setThemeApp(tab.position)
                    }
                    2 -> {
                        setThemeApp(tab.position)
                    }
                }
               requireActivity().recreate()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        setThemeApp(tab.position)
                    }
                    1 -> {
                        setThemeApp(tab.position)
                    }
                    2 -> {
                        setThemeApp(tab.position)
                    }
                }
            }
        })
    }

    private fun setThemeApp(numberTheme: Int) {
        val sharedPreferences = requireActivity()
            .getSharedPreferences("KEY_SP_THEME", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("KEY_THEME", numberTheme)
        editor.apply()
    }

    private fun getThemeApp(): Int {
        val sharedPreferences = requireActivity()
            .getSharedPreferences("KEY_SP_THEME", AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt("KEY_THEME", 0)
    }
}