package com.myblogtour.blogtour.ui.setting

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.myblogtour.blogtour.databinding.FragmentSettingBinding
import com.myblogtour.blogtour.utils.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    lateinit var tabBtn: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabBtn = binding.tabGroup
        tabBtn.addTab(tabBtn.newTab().setText("Системная"))
        tabBtn.addTab(tabBtn.newTab().setText("Светлая"))
        tabBtn.addTab(tabBtn.newTab().setText("Темная"))

    }
}