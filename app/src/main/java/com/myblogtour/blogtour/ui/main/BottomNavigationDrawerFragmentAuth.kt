package com.myblogtour.blogtour.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.BottomNavigationLayoutAuthBinding
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment
import com.myblogtour.blogtour.ui.setting.SettingFragment
import com.myblogtour.blogtour.utils.BaseDialogFragment

class BottomNavigationDrawerFragmentAuth : BaseDialogFragment<BottomNavigationLayoutAuthBinding>(BottomNavigationLayoutAuthBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationViewAuth.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navBtnAuthProfile -> {
                    toFragment(AuthUserFragment())
                    this.dismiss()
                }
                R.id.navBtnSetting -> {
                    toFragment(SettingFragment())
                    this.dismiss()
                }
            }
            true
        }
    }

    private fun toFragment(f: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, f)
            .addToBackStack("")
            .commit()
    }
}