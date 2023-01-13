package com.myblogtour.blogtour.ui.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentProfileBinding
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment
import com.myblogtour.blogtour.utils.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        binding.singOut.setOnClickListener {
            viewModel.singInOut()
        }

    }

    private fun initViewModel() {
        viewModel.userSuccess.observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.userSingOut.observe(viewLifecycleOwner) {
            toFragment()
        }

        viewModel.onRefresh()
    }

    private fun toFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, AuthUserFragment())
            .commit()
    }

    private fun renderData(user: FirebaseUser?) {
        binding.userLogin.text = user!!.displayName
    }
}