package com.myblogtour.blogtour.ui.authUser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.appState.AppStateUserAuth
import com.myblogtour.blogtour.databinding.FragmentAuthUserBinding
import com.myblogtour.blogtour.ui.profile.ProfileFragment
import com.myblogtour.blogtour.ui.registrationUser.RegistrationUserFragment
import com.myblogtour.blogtour.utils.BaseFragment

class AuthUserFragment : BaseFragment<FragmentAuthUserBinding>(FragmentAuthUserBinding::inflate) {

    private val viewModel: AuthUserViewModel by lazy {
        ViewModelProvider(this)[AuthUserViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        initBtn()
    }

    private fun initBtn() {
        with(binding) {
            btnClickEnter.setOnClickListener {
                viewModel.authUser(inputEmailAuth.text, inputPasswordAuth.text)
            }
            btnClickRegister.setOnClickListener {
                toFragment(RegistrationUserFragment())
            }
        }
    }

    private fun toFragment(f: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment, f).commit()
    }

    private fun renderData(it: AppStateUserAuth) {
        with(binding) {
            when (it) {
                is AppStateUserAuth.ErrorNullPassword -> {
                    inputPasswordAuth.error = it.errorNullPassword
                }
                is AppStateUserAuth.ErrorUser -> {
                    inputPasswordAuth.error = ""
                    inputEmailAuth.error = ""
                    Toast.makeText(context, it.errorUser, Toast.LENGTH_SHORT).show()
                }
                is AppStateUserAuth.ErrorValidEmail -> {
                    inputEmailAuth.error = it.errorValidEmail
                }
                is AppStateUserAuth.SuccessUser -> {
                    toFragment(ProfileFragment())
                }
            }
        }
    }
}