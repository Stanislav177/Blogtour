package com.myblogtour.blogtour.ui.addPublication

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import coil.load
import com.myblogtour.blogtour.databinding.FragmentAddPublicationBinding
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPublicationFragment :
    BaseFragment<FragmentAddPublicationBinding>(FragmentAddPublicationBinding::inflate) {

    private val REQUEST_CODE = 999
    private val viewModel: AddPublicationViewModel by viewModel()
    private var imageUri: Uri? = null
    private var imageUriLocal: Uri? = null
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                imageUriLocal = it
                viewModel.image(it)
                exifInter()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserve()
        with(binding) {
            publishBtnAddPost.setOnClickListener {
                viewModel.dataPublication(
                    editTextPost.text.toString(),
                    editTextLocation.text.toString(),
                    imageUri
                )
            }
            attachPhotoAddPost.setOnClickListener {
                resultLauncher.launch("image/*")
            }
            deleteImagePublication.setOnClickListener {
                viewModel.deleteImage()
            }
            currentLocation.setOnClickListener {
                checkPermission()
            }
        }
    }

    private fun exifInter() {
        val exif =
            ExifInterface(requireContext().contentResolver.openInputStream(imageUriLocal!!)!!)
        val locationImage = FloatArray(2)
        val latLon = exif.getLatLong(locationImage)
        val lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
        val lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
    }

    private fun viewModelObserve() {
        with(viewModel) {
            publishPostLiveData.observe(viewLifecycleOwner) {
                publishPost(it)
            }
            loadUri.observe(viewLifecycleOwner) {
                initImagePublication(it)
            }
            progressLoad.observe(viewLifecycleOwner) {
                with(binding) {
                    progressBarImagePostAddPost.visibility = View.VISIBLE
                    textViewProgress.visibility = View.VISIBLE
                    textViewProgress.text = "$it%"
                }
            }
            errorMessageImage.observe(viewLifecycleOwner) {
                showToast(it)
            }
            errorMessageLocation.observe(viewLifecycleOwner) {
                showToast(it)
            }
            errorMessageText.observe(viewLifecycleOwner) {
                showToast(it)
            }
            errorMessagePublicationAdd.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }
    }

    private fun showToast(it: String?) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun initImagePublication(it: Uri?) {
        with(binding) {
            if (it != null) {
                imageUri = it
                progressBarImagePostAddPost.visibility = View.GONE
                textViewProgress.visibility = View.GONE
                imagePostAddPost.load(imageUriLocal)
                deleteImagePublication.visibility = View.VISIBLE
            } else {
                imagePostAddPost.load(null)
                deleteImagePublication.visibility = View.GONE
            }
        }
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialog()
                }
                else -> {
                    myRequestPermission()
                }
            }
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Доступ к геопозиции")
            .setMessage(
                "Разрешить доступ к геопозиции, " +
                        "для автоматического определения вашего место положения"
            )
            .setPositiveButton("Да") { _, _ ->
                myRequestPermission()
            }.setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

    private fun getLocation() {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }

    private fun publishPost(it: Boolean) {
        if (it) {
            showToast("Пост размещен")
            with(binding) {
                viewModel.deleteImage()
                viewModel.flagAddPublication(true)
                editTextPost.text.clear()
                editTextLocation.text.clear()
                imagePostAddPost.load(null)
            }
        } else {
            showToast("Что-то пошло не так")
        }
    }


    override fun onDetach() {
        super.onDetach()
        viewModel.deleteImageDetach()
    }
}