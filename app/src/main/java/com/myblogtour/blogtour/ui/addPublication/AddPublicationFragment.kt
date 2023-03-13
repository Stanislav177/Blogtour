package com.myblogtour.blogtour.ui.addPublication

import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.myblogtour.blogtour.databinding.FragmentAddPublicationBinding
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPublicationFragment :
    BaseFragment<FragmentAddPublicationBinding>(FragmentAddPublicationBinding::inflate) {

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
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            errorMessageLocation.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            errorMessageText.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun publishPost(it: Boolean) {
        if (it) {
            Toast.makeText(requireContext(), "Пост размещен", Toast.LENGTH_SHORT).show()
            with(binding) {
                editTextPost.text.clear()
                editTextLocation.text.clear()
                imagePostAddPost.load(null)
            }
        } else {
            Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.deleteImageDetach()
    }
}