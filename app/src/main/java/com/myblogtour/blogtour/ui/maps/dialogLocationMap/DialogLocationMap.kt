package com.myblogtour.blogtour.ui.maps.dialogLocationMap

import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.databinding.DialogCustomMapLocationBinding
import com.myblogtour.blogtour.utils.BaseDialogFragment

class DialogLocationMap :
    BaseDialogFragment<DialogCustomMapLocationBinding>(DialogCustomMapLocationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOkLocation.setOnClickListener {
            dismiss()
        }
        arguments?.getString("ADDRESS")?.let {
            binding.locationMap.text = it
        }
    }

    companion object {
        fun newInstance(address: String?) = DialogLocationMap().apply {
            arguments = Bundle().apply {
                putString("ADDRESS", address)
            }
        }
    }
}