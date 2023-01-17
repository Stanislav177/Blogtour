package com.myblogtour.blogtour.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentProfileBinding
import com.myblogtour.blogtour.ui.addPost.AddPostFragment
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment
import com.myblogtour.blogtour.utils.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initButton()
    }

    private fun initButton() {
        with(binding) {
            singOut.setOnClickListener {
                viewModel.singInOut()
            }
            publishPostProfile.setOnClickListener {
                toFragment(AddPostFragment())
            }
        }
    }

    private fun initViewModel() {
        viewModel.userSuccess.observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.userSingOut.observe(viewLifecycleOwner) {
            toFragment(AuthUserFragment())
        }

        viewModel.onRefresh()
    }

    private fun toFragment(f: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, f)
            .commit()
    }

    private fun renderData(user: FirebaseUser?) {
        binding.userLogin.text = user!!.displayName
    }
}