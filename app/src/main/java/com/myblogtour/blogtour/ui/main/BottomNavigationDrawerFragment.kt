package com.myblogtour.blogtour.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.BottomNavigationLayoutBinding
import com.myblogtour.blogtour.ui.addPublication.AddPublicationFragment
import com.myblogtour.blogtour.ui.myPublication.MyPublicationFragment
import com.myblogtour.blogtour.ui.profileUser.ProfileFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
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
                R.id.navBtnFavoritePublication -> {
                    Toast.makeText(requireActivity(), "FavoritePublication", Toast.LENGTH_SHORT)
                        .show()
                    this.dismiss()
                }
                R.id.navBtnSetting -> {
                    Toast.makeText(requireActivity(), "Setting", Toast.LENGTH_SHORT).show()
                    this.dismiss()
                }
                R.id.navBtnLoadPublication -> {
                    toFragment(AddPublicationFragment())
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