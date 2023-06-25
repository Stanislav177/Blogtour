package com.myblogtour.blogtour.ui.privacyPolicy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myblogtour.blogtour.databinding.DialogCustomPolicyBinding
import com.myblogtour.blogtour.utils.BaseDialogFragment

class DialogPrivacyPolicy :
    BaseDialogFragment<DialogCustomPolicyBinding>(DialogCustomPolicyBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.okPrivacyPolicy.setOnClickListener {
            dismiss()
        }
    }
}