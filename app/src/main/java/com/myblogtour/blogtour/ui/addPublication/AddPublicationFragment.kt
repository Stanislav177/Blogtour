package com.myblogtour.blogtour.ui.addPublication

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.myblogtour.blogtour.databinding.FragmentAddPublicationBinding
import com.myblogtour.blogtour.domain.ImagePublicationEntity
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddPublicationFragment :
    BaseFragment<FragmentAddPublicationBinding>(FragmentAddPublicationBinding::inflate),
    MyOnClickListenerPosition {

    private val REQUEST_CODE = 999
    private val MIN_DISTANCE = 10f
    private val MIN_PERIOD = 15L

    private val viewModel: AddPublicationViewModel by viewModel()

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                addImagePublication(it)
            }
        }

    private val locationManager by lazy {
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val adapterImage: AddPublicationImageAdapter by lazy {
        AddPublicationImageAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserve()
        with(binding) {
            rvImageAddPublication.adapter = adapterImage
            rvImageAddPublication.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            publishBtnAddPost.setOnClickListener {
                viewModel.dataPublication(
                    editTextPost.text.toString(),
                    editTextLocation.text.toString()
                )
            }
            attachPhotoAddPost.setOnClickListener {
                viewModel.getLoadingImage()
            }
            currentLocation.setOnClickListener {
                checkPermissionLocation()
            }
        }
    }


    private fun addImagePublication(it: Uri) {
        viewModel.image(it)
        adapterImage.setImageList(ImagePublicationEntity(uriLocal = it))
    }

    private fun exifInter() {
//        val exif =
//            ExifInterface(requireContext().contentResolver.openInputStream(imageUriLocal!!)!!)
//        val locationImage = FloatArray(2)
//        val latLon = exif.getLatLong(locationImage)
//        val lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
//        val lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
        TODO("FM - не приходят координаты")
    }

    private fun viewModelObserve() {
        with(viewModel) {
            publishPostLiveData.observe(viewLifecycleOwner) {
                publishPost(it)
            }
            loadUriImage.observe(viewLifecycleOwner) {
                adapterImage.replaceImage(it)
            }
            progressLoad.observe(viewLifecycleOwner) {
                adapterImage.replaceImage(it)
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
            address.observe(viewLifecycleOwner) {
                binding.editTextLocation.text = it
            }
            counterImage.observe(viewLifecycleOwner) {
                if (it) {
                    resultLauncher.launch("image/*")
                } else {
                    showToast("Лимит привышен")
                }
            }
            loadingImage.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.getCounterImage()
                } else {
                    showToast("Идет загрузка изображения")
                }
            }
        }
    }

    private fun showToast(it: String?) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun checkPermissionLocation() {
        when {
            checkPermission() -> {
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

    private fun getLocation() {
        if (checkPermission()) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val providerGPS = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                providerGPS?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_PERIOD,
                        MIN_DISTANCE,
                        locationListener)
                }
            } else {
                showToast("GPS выключен")
            }
        } else {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            setViewModelLocation(location.latitude, location.longitude)
        }
    }

    private fun setViewModelLocation(lat: Double?, lon: Double?) {
        viewModel.getAddress(lat, lon)
        locationManager.removeUpdates(locationListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == REQUEST_CODE) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialog()
                }
                else -> {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            }
        }
    }

    private fun publishPost(it: Boolean) {
        if (it) {
            showToast("Пост размещен")
            with(binding) {
                viewModel.deleteImage()
                viewModel.flagAddPublication(true)
                editTextPost.text.clear()
                editTextLocation.text.clear()
                //imagePostAddPost.load(null)
            }
        } else {
            showToast("Что-то пошло не так")
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteImage()
        locationManager.removeUpdates(locationListener)
    }

    override fun onItemClick(uriLocal: Uri) {
        viewModel.deleteImage(uriLocal)
    }

    override fun onItemClickCancel(uriLocal: Uri) {
        viewModel.cancel(uriLocal)
    }
}
