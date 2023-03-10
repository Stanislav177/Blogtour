package com.myblogtour.blogtour.ui.privacyPolicy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.myblogtour.blogtour.databinding.DialogCustomPolicyBinding

class DialogPrivacyPolicy : DialogFragment() {

    private var _binding: DialogCustomPolicyBinding? = null
    private val binding: DialogCustomPolicyBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomPolicyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.okPrivacyPolicy.setOnClickListener {
            dismiss()
        }
    }
}