package com.myblogtour.blogtour.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myblogtour.blogtour.databinding.BottomSheetSearchBinding

class BottomSheetSearch : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSearchBinding? = null
    private val binding: BottomSheetSearchBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textInputLayout.setEndIconOnClickListener {
            Toast.makeText(requireActivity(), "Не найдено", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}