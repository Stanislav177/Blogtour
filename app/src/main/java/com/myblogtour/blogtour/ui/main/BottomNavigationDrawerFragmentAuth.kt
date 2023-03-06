package com.myblogtour.blogtour.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.BottomNavigationLayoutAuthBinding
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment

class BottomNavigationDrawerFragmentAuth : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutAuthBinding? = null
    private val binding: BottomNavigationLayoutAuthBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationViewAuth.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navBtnAuthProfile -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.containerFragment, AuthUserFragment())
                        .commit()
                    this.dismiss()
                }
                R.id.navBtnSetting -> {
                    this.dismiss()
                }
            }
            true
        }
    }
}