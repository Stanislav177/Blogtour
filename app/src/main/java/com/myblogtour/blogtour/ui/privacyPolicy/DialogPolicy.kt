package com.myblogtour.blogtour.ui.privacyPolicy

import android.content.Context
import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.DialogCustomPolicyBinding
import com.myblogtour.blogtour.utils.BaseDialogFragment

const val POLITIC = 999
const val TERMS = 111

class DialogPolicy :
    BaseDialogFragment<DialogCustomPolicyBinding>(DialogCustomPolicyBinding::inflate) {

    private var statusText = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        statusText = arguments?.getInt("STATUS")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            if (statusText == POLITIC) {
                textDialog.text = requireActivity().resources.getText(R.string.privacy_policy_text)
            } else{
                textDialog.text = requireActivity().resources.getText(R.string.terms_of_yandex)
            }
            okPrivacyPolicy.setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(status: Int) = DialogPolicy().apply {
            arguments = Bundle().apply {
                putInt("STATUS", status)
            }
        }
    }
}