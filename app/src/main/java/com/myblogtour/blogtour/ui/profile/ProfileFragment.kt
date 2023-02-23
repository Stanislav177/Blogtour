package com.myblogtour.blogtour.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentProfileBinding
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.ui.addPublication.AddPublicationFragment
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment
import com.myblogtour.blogtour.ui.myPublication.MyPublicationFragment
import com.myblogtour.blogtour.utils.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }
    private lateinit var uidUser: String

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
                toFragment(AddPublicationFragment())
            }
            myPublication.setOnClickListener {
                toFragment(MyPublicationFragment(uidUser))
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

    private fun renderData(user: UserProfileEntity) {
        with(binding) {
            uidUser = user.uid
            userLogin.text = user.nickname
            iconUserProfile.load(user.icon)
            progressBarProfileUser.visibility = View.GONE
        }
    }
}