package com.myblogtour.blogtour.ui.registrationUser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.myblogtour.blogtour.appState.AppStateUserRegistration
import com.myblogtour.blogtour.databinding.FragmentRegistrationUserBinding
import com.myblogtour.blogtour.utils.BaseFragment
import com.myblogtour.blogtour.utils.validatorEmail.EmailValidatorPatternImpl

class RegistrationUserFragment :
    BaseFragment<FragmentRegistrationUserBinding>(FragmentRegistrationUserBinding::inflate) {

    private val auth by lazy { Firebase.auth }

    private val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    private var nameUser = "User"
    private var email: String? = null
    private var password: String? = null
    private var flagCorrectEmail = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderUser(it)
        }

        binding.btnClickRegisterUser.setOnClickListener {
            viewModel.setEmailValidator(binding.inputEmailReg.text, binding.inputPasswordReg.text, binding.inputPasswordConfirmReg.text)
        }
    }

    private fun renderUser(it: AppStateUserRegistration?) {
        when (it) {
            is AppStateUserRegistration.ErrorEmail -> {}
            is AppStateUserRegistration.ErrorPassword -> {}
            is AppStateUserRegistration.SuccessEmail -> {}
            is AppStateUserRegistration.SuccessPassword -> {}
            null -> {}
        }
    }
}