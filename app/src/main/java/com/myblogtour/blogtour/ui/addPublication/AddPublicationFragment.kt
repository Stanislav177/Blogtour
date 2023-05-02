package com.myblogtour.blogtour.ui.addPublication

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.ExifInterface
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import coil.load
import com.myblogtour.blogtour.databinding.FragmentAddPublicationBinding
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddPublicationFragment :
    BaseFragment<FragmentAddPublicationBinding>(FragmentAddPublicationBinding::inflate) {

    private val REQUEST_CODE = 999
    private val MIN_DISTANCE = 10f
    private val MIN_PERIOD = 15L
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

    private lateinit var locationManager: LocationManager

//    private val locationManager by lazy {
//        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//    }

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
                checkPermissionLocation()
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
            address.observe(viewLifecycleOwner) {
                binding.editTextLocation.text = it
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

    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE)
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
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
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val wifiMan = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
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
            } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                val providerNetwork =
                    locationManager.getProvider(LocationManager.NETWORK_PROVIDER)
                providerNetwork?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        2000L,
                        0f,
                        locationListener
                    )
                }
            } else {
                val lastLocation =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                lastLocation?.let { loc ->
                    setViewModelLocation(loc.latitude, loc.longitude)
                }
            }
        } else {
            showToast("ЧТО_То пошло не так ")
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            setViewModelLocation(location.latitude, location.longitude)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
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
                imagePostAddPost.load(null)
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

    override fun onDetach() {
        super.onDetach()
        viewModel.deleteImageDetach()
    }
}
