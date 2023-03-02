package com.myblogtour.blogtour.ui.recoveryPassword

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.appState.AppStateResetPassword
import com.myblogtour.blogtour.databinding.FragmentRecoveryPasswordBinding
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecoveryPasswordFragment :
    BaseFragment<FragmentRecoveryPasswordBinding>(FragmentRecoveryPasswordBinding::inflate) {

    private val viewModel: RecoveryPasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            answerResetPassword(it)
        }
    }

    private fun answerResetPassword(it: AppStateResetPassword) {
        when (it) {
            is AppStateResetPassword.NoResetPasswordState -> {
                binding.inputPasswordRecovery.error = it.errorResetPassword
            }
            is AppStateResetPassword.ResetPasswordState -> {
                binding.inputPasswordRecovery.error = null
                openAlertDialogResetPassword()
            }
        }
    }

    private fun openAlertDialogResetPassword() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Запрос отправлен")
            .setMessage("Для востановления пароля проверьте почту")
            .setPositiveButton("Закрыть", onClickListener())
        val dialog = builder.create()
        dialog.show()
    }

    private fun onClickListener() = DialogInterface.OnClickListener { _, _ ->
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun initView() {
        with(binding) {
            btnRecovery.setOnClickListener {
                viewModel.resetPassword(editTextEmail.text)
            }
        }
    }
}