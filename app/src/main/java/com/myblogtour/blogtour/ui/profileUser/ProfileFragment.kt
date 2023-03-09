package com.myblogtour.blogtour.ui.profileUser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentProfileBinding
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment
import com.myblogtour.blogtour.ui.profileUser.resetPassword.DialogResetPasswordFragment
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var email: String

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
            textBtnVerificationEmail.setOnClickListener {
                viewModel.verificationEmail()
            }
            btnUserProfileResetPassword.setOnClickListener {
                val dialog = DialogResetPasswordFragment.newInstance(email)
                dialog.show(requireActivity().supportFragmentManager.beginTransaction(), "")
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
        viewModel.verificationEmail.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.onRefresh()
    }

    private fun toFragment(f: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, f)
            .addToBackStack("")
            .commit()
    }

    private fun renderData(user: UserProfileEntity) {
        with(binding) {
            userLogin.text = user.nickname
            iconUserProfile.load(user.icon)
            progressBarProfileUser.visibility = View.GONE
            mailUser.text = user.email
            email = user.email
            if (user.verification) {
                iconVerifiedUser.setImageResource(R.drawable.ic_verified_user)
                textBtnVerificationEmail.isClickable = false
                textBtnVerificationEmail.text = "Подтвержденный аккаунт"
            }
        }
    }
}