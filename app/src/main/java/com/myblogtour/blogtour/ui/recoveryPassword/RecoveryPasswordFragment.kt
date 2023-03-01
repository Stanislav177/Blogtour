package com.myblogtour.blogtour.ui.recoveryPassword

import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.databinding.FragmentRecoveryPasswordBinding
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecoveryPasswordFragment :
    BaseFragment<FragmentRecoveryPasswordBinding>(FragmentRecoveryPasswordBinding::inflate) {

    private val viewModel: RecoveryPasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        with(binding) {
            btnRecovery.setOnClickListener {
                viewModel.resetPassword(editTextEmail.text.toString())
            }
        }
    }
}