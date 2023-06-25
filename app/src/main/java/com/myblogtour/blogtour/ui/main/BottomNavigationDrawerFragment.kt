package com.myblogtour.blogtour.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.BottomNavigationLayoutBinding
import com.myblogtour.blogtour.ui.aboutApp.AboutAppFragment
import com.myblogtour.blogtour.ui.addPublication.AddPublicationFragment
import com.myblogtour.blogtour.ui.myPublication.MyPublicationFragment
import com.myblogtour.blogtour.ui.profileUser.ProfileFragment
import com.myblogtour.blogtour.ui.setting.SettingFragment
import com.myblogtour.blogtour.utils.BaseDialogFragment

class BottomNavigationDrawerFragment :
    BaseDialogFragment<BottomNavigationLayoutBinding>(BottomNavigationLayoutBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navBtnPersonalDataUserProfile -> {
                    toFragment(ProfileFragment())
                    this.dismiss()
                }
                R.id.navBtnUserPublication -> {
                    toFragment(MyPublicationFragment())
                    this.dismiss()
                }
                R.id.navBtnSetting -> {
                    toFragment(SettingFragment())
                    this.dismiss()
                }
                R.id.navBtnLoadPublication -> {
                    toFragment(AddPublicationFragment())
                    this.dismiss()
                }
                R.id.navBtnAboutApp -> {
                    toFragment(AboutAppFragment())
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