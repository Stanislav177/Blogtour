package com.myblogtour.blogtour.ui.registrationUser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.appState.AppStateUserRegistration
import com.myblogtour.blogtour.databinding.FragmentRegistrationUserBinding
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment
import com.myblogtour.blogtour.utils.BaseFragment

class RegistrationUserFragment :
    BaseFragment<FragmentRegistrationUserBinding>(FragmentRegistrationUserBinding::inflate) {

    private val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderUser(it)
        }
        initViewBtn()
    }

    private fun initViewBtn() {
        with(binding) {
            btnClickRegisterUser.setOnClickListener {
                viewModel.registerUserFb(
                    inputLoginReg.text,
                    inputEmailReg.text,
                    inputPasswordReg.text,
                    inputPasswordConfirmReg.text)
            }
            btnClickOkRegister.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.containerFragment, AuthUserFragment()).commit()
            }
        }
    }

    private fun renderUser(it: AppStateUserRegistration) {
        with(binding) {
            when (it) {
                is AppStateUserRegistration.ErrorEmail -> {
                    inputEmailReg.error = it.errorEmail
                }
                is AppStateUserRegistration.ErrorPassword -> {
                    inputPasswordReg.error = it.errorPassword
                }
                is AppStateUserRegistration.SuccessUser -> {
                    closeFragmentRegisterUser()
                }
                is AppStateUserRegistration.ErrorUser -> {
                    Toast.makeText(context, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                }
                is AppStateUserRegistration.ErrorPasswordEquals -> {
                    val errorPassEquals = it.errorPasswordEquals
                    inputPasswordConfirmReg.error = errorPassEquals
                    inputPasswordConfirmReg.error = errorPassEquals
                }
                is AppStateUserRegistration.ErrorUserLogin -> {
                    inputLoginReg.error = it.errorUserName
                }
            }
        }
    }

    private fun closeFragmentRegisterUser() {
        with(binding) {
            registerView.visibility = View.GONE
            successfulRegister.visibility = View.VISIBLE
        }
    }
}