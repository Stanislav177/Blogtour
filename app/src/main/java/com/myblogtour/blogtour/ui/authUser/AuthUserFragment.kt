package com.myblogtour.blogtour.ui.authUser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.appState.AppStateUserAuth
import com.myblogtour.blogtour.databinding.FragmentAuthUserBinding
import com.myblogtour.blogtour.ui.profileUser.ProfileFragment
import com.myblogtour.blogtour.ui.recoveryPassword.RecoveryPasswordFragment
import com.myblogtour.blogtour.ui.registrationUser.RegistrationUserFragment
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthUserFragment : BaseFragment<FragmentAuthUserBinding>(FragmentAuthUserBinding::inflate) {

    private val viewModel: AuthUserViewModel by viewModel()

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
            btnRecoveryPassword.setOnClickListener {
                toFragment(RecoveryPasswordFragment())
            }
        }
    }

    private fun toFragment(f: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment, f)
            .addToBackStack("")
            .commit()
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
                is AppStateUserAuth.SuccessUserNoVerified -> {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle("Для авторизации необходимо, подтвердить email.")
                        .setMessage("Отправить повторно электронное письмо с подтверждением email?")
                        .setPositiveButton("Да") { _, _ ->
                            viewModel.sendEmailVerification()
                        }
                        .setNegativeButton("Нет") { _, _ ->
                            viewModel.singOut()
                        }.create().show()
                }
                is AppStateUserAuth.SendVerification -> {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle(it.verification).setPositiveButton("Закрыть") { _, _ ->

                    }.create().show()
                }
            }
        }
    }
}