package com.myblogtour.blogtour.ui.registrationUser

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.myblogtour.blogtour.appState.AppStateUserRegistration
import com.myblogtour.blogtour.databinding.FragmentRegistrationUserBinding
import com.myblogtour.blogtour.ui.privacyPolicy.DialogPolicy
import com.myblogtour.blogtour.ui.privacyPolicy.POLITIC
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationUserFragment :
    BaseFragment<FragmentRegistrationUserBinding>(FragmentRegistrationUserBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModel()

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                binding.iconUserProfileRegistration.setImageURI(it)
                viewModel.loadingIconUserProfile(it)
            }
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
                    inputPasswordConfirmReg.text
                )
            }

            addIconUserProfile.setOnClickListener {
                resultLauncher.launch("image/*")
            }
            deleteIconRegisterUser.setOnClickListener {
                viewModel.deleteImage()
            }
            btnPrivacyPolicy.setOnClickListener {
                val dialog = DialogPolicy.newInstance(POLITIC)
                dialog.show(requireActivity().supportFragmentManager, "")
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
                    openAlertDialog()
                    viewModel.singOut()
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
                is AppStateUserRegistration.ProgressLoadingIconUser -> {
                    with(binding) {
                        addIconUserProfile.visibility = View.GONE
                        progressLoadingIconUser.visibility = View.VISIBLE
                        percentLoadingIcon.text = "${it.progress} %"
                    }
                }
                is AppStateUserRegistration.SuccessIconUser -> {
                    with(binding) {
                        progressLoadingIconUser.visibility = View.GONE
                        percentLoadingIcon.visibility = View.GONE
                        deleteIconRegisterUser.visibility = View.VISIBLE
                    }
                }
                is AppStateUserRegistration.DeleteIconUser -> {
                    with(binding) {
                        binding.iconUserProfileRegistration.setImageURI(null)
                        deleteIconRegisterUser.visibility = View.GONE
                        addIconUserProfile.visibility = View.VISIBLE
                    }
                }
                is AppStateUserRegistration.ErrorIconUser -> {
                    Toast.makeText(requireActivity(), "Добавьте изображение", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun openAlertDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder
            .setTitle("Регистрация прошла успешно.")
            .setMessage(" Для подтверждения Email, на почту отправлено письмо")
            .setPositiveButton("OK") { _, _ ->
                requireActivity().supportFragmentManager.popBackStack()
            }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteImageDetach()
    }
}