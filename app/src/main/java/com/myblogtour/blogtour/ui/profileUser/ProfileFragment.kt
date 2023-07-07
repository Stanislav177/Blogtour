package com.myblogtour.blogtour.ui.profileUser

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentProfileBinding
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.ui.authUser.AuthUserFragment
import com.myblogtour.blogtour.ui.profileUser.resetPassword.DialogResetPasswordFragment
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var userLocal: UserProfileEntity
    private lateinit var email: String
    private lateinit var dpd: DatePickerDialog

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { uri ->
                changePhotoUser(uri)
            }
        }

    private fun changePhotoUser(uri: Uri) {
        viewModel.changeImageProfileUser(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initButton()
        initViewSpinnerGender()
    }

    private fun initViewSpinnerGender() {
        val listGender = resources.getStringArray(R.array.listGender)
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            listGender
        )
        with(binding.genderUserSpinner) {
            this.adapter = adapter
        }
    }

    private fun initButton() {
        with(binding) {
            singOut.setOnClickListener {
                viewModel.singInOut()
                toFragment(AuthUserFragment())
            }
            textBtnVerificationEmail.setOnClickListener {
                viewModel.verificationEmail()
            }
            btnUserProfileResetPassword.setOnClickListener {
                val dialog = DialogResetPasswordFragment.newInstance(email)
                dialog.show(requireActivity().supportFragmentManager.beginTransaction(), "")
            }
            tvDateOfBirthUser.setOnClickListener {
                initDatePickerDialog()
            }
            btnSaveProfileUser.setOnClickListener {
                binding.progressBarSaveProfile.visibility = View.VISIBLE
                viewModel.saveReadUserProfile(
                    binding.userLogin.text,
                    binding.userLocation.text.toString(),
                    binding.genderUserSpinner.selectedItemPosition,
                    binding.tvDateOfBirthUser.text.toString()
                )
            }
            changePhotoUserProfile.setOnClickListener {
                resultLauncher.launch("image/*")
                it.isClickable = false
            }
        }
    }

    private fun initDatePickerDialog() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(userLocal.datebirth)
        val cal = Calendar.getInstance()
        if (date != null) {
            cal.time = date
        }
        dpd = DatePickerDialog(
            requireActivity(),
            { _, i, i2, i3 ->
                binding.tvDateOfBirthUser.text = "$i-${i2 + 1}-$i3"
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show()
    }

    private fun initViewModel() {
        with(viewModel) {
            userSuccess.observe(viewLifecycleOwner) {
                renderData(it)
            }
            userSingOut.observe(viewLifecycleOwner) {
                toFragment(AuthUserFragment())
            }
            verificationEmail.observe(viewLifecycleOwner) {
                showToast(it)
            }
            errorSaveProfile.observe(viewLifecycleOwner) {
                binding.progressBarSaveProfile.visibility = View.GONE
                showToast(it)
            }
            successSaveUserProfile.observe(viewLifecycleOwner) {
                binding.progressBarSaveProfile.visibility = View.GONE
                showToast(it)
            }
            errorLocationUser.observe(viewLifecycleOwner) {
                binding.progressBarSaveProfile.visibility = View.GONE
                binding.userLocation.error = it
                showToast(it)
            }
            errorUserLogin.observe(viewLifecycleOwner) {
                binding.progressBarSaveProfile.visibility = View.GONE
                binding.userLogin.error = it
            }
            progressLoadingImage.observe(viewLifecycleOwner) {
                with(binding) {
                    progressBarProfileUser.visibility = View.VISIBLE
                    progressLoadingImagePercentProfileUser.visibility = View.VISIBLE
                    progressLoadingImagePercentProfileUser.text = "${it.progress}%"
                }
            }
            onSuccessLoadingImageUser.observe(viewLifecycleOwner) {
                with(binding) {
                    progressBarProfileUser.visibility = View.GONE
                    progressLoadingImagePercentProfileUser.visibility = View.GONE
                    imageUserProfile.load(it) {
                        transformations(CircleCropTransformation())
                    }
                    changePhotoUserProfile.isClickable = true
                }
            }
            onRefresh()
        }
    }

    private fun showToast(it: String?) {
        Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
    }

    private fun toFragment(f: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, f)
            .addToBackStack("")
            .commit()
    }

    private fun renderData(user: UserProfileEntity) {
        userLocal = user
        with(binding) {
            userLogin.text = user.nickname.toEditable()
            imageUserProfile.load(user.icon) {
                placeholder(R.drawable.ic_load_image)
                transformations(CircleCropTransformation())
            }
            progressBarProfileUser.visibility = View.GONE
            mailUser.text = user.email
            email = user.email
            if (user.verification) {
                iconVerifiedUser.setImageResource(R.drawable.ic_verified_user)
                textBtnVerificationEmail.isClickable = false
                textBtnVerificationEmail.text = "Подтвержденный аккаунт"
            }
            userLocation.text = user.location.toEditable()
            genderUserSpinner.setSelection(user.usergender)
            tvDateOfBirthUser.text = user.datebirth
        }
    }

    private fun String.toEditable() = Editable.Factory.getInstance().newEditable(this)
}