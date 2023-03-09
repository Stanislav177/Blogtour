package com.myblogtour.blogtour.ui.profileUser.resetPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.myblogtour.blogtour.databinding.DialogCustomResetPasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogResetPasswordFragment : DialogFragment() {
    private lateinit var emailUser: String

    private var _binding: DialogCustomResetPasswordBinding? = null
    private val binding: DialogCustomResetPasswordBinding
        get() {
            return _binding!!
        }

    private val viewModel: ViewModelResetPassword by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            emailUser = it.getString("MAIL", " ")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomResetPasswordBinding.inflate(
            layoutInflater,
            container,
            false
        )
        initViewModel()
        initBtn()
        return binding.root
    }

    private fun initViewModel() {
        with(viewModel) {
            reAuthentication.observe(viewLifecycleOwner) {
                reAuthUser(it)
            }
            patternPassword.observe(viewLifecycleOwner) {
                password(it)
            }
            patternPasswordConfirm.observe(viewLifecycleOwner) {
                passwordConfirm(it)
            }
            errorReAuthentication.observe(viewLifecycleOwner) {
                binding.localTILPassword.error = it
            }
            updatePasswordUser.observe(viewLifecycleOwner){
                updatePassword(it)
            }
        }

    }

    private fun updatePassword(it: Boolean) {
        if (it){
            Toast.makeText(requireActivity(), "Пароль успешно изменен", Toast.LENGTH_SHORT).show()
            dismiss()
        } else{
            Toast.makeText(requireActivity(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun passwordConfirm(it: String) {
        with(binding) {
            localTILPasswordNew.isErrorEnabled = false
            localTILPasswordNewConfirm.error = it
        }
    }

    private fun password(it: String) {
        with(binding) {
            localTILPasswordNew.error = it
        }
    }

    private fun reAuthUser(it: Boolean) {
        with(binding) {
            if (it) {
                localTILPassword.isErrorEnabled = false
                viewModel.newPassword(
                    editTextNewPassword.editableText,
                    editTextNewPasswordConfirm.editableText
                )
            } else {
                localTILPassword.error = "Пароль не верный"
            }
        }
    }

    private fun initBtn() {
        with(binding) {
            btnPositiv.setOnClickListener {
                viewModel.reAuth(emailUser, binding.editTextCurrentPassword.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(mail: String) = DialogResetPasswordFragment().apply {
            arguments = Bundle().apply {
                putString("MAIL", mail)
            }
        }
    }
}