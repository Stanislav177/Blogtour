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
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import coil.load
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentAddPublicationBinding
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddPublicationFragment :
    BaseFragment<FragmentAddPublicationBinding>(FragmentAddPublicationBinding::inflate) {

    private val REQUEST_CODE = 999
    private val MIN_DISTANCE = 10f
    private val MIN_PERIOD = 15L

    private val linearLayoutOne: LinearLayoutCompat by lazy { LinearLayoutCompat(requireActivity()) }
    private val linearLayoutTwo: LinearLayoutCompat by lazy { LinearLayoutCompat(requireActivity()) }
    private val linearLayoutThree: LinearLayoutCompat by lazy { LinearLayoutCompat(requireActivity()) }
    private val deleteImageOne: AppCompatTextView by lazy { AppCompatTextView(requireActivity()) }
    private val deleteImageTwo: AppCompatTextView by lazy { AppCompatTextView(requireActivity()) }
    private val deleteImageThree: AppCompatTextView by lazy { AppCompatTextView(requireActivity()) }
    private val imageViewOne: AppCompatImageView by lazy { AppCompatImageView(requireActivity()) }
    private val imageViewTwo: AppCompatImageView by lazy { AppCompatImageView(requireActivity()) }
    private val imageViewThree: AppCompatImageView by lazy { AppCompatImageView(requireActivity()) }
    private val progressLoadingImage: ProgressBar by lazy { ProgressBar(requireActivity()) }
    private var imageOneLocal: Uri? = null
    private var imageTwoLocal: Uri? = null
    private var imageThreeLocal: Uri? = null
    private var imageOne: Uri? = null
    private var imageTwo: Uri? = null
    private var imageThree: Uri? = null

    private var flagIsClickableAttachImage = true

    private val viewModel: AddPublicationViewModel by viewModel()

    //private var listUriImage: MutableList<Uri> = mutableListOf()
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                addView(it)
            }
        }

    private val locationManager by lazy {
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserve()
        with(binding) {
            publishBtnAddPost.setOnClickListener {
                viewModel.dataPublication(
                    editTextPost.text.toString(),
                    editTextLocation.text.toString(),
                    imageOne, imageTwo, imageThree
                )
            }
            attachPhotoAddPost.setOnClickListener {
                viewModel.counterImage()
            }
            currentLocation.setOnClickListener {
                checkPermissionLocation()
            }
        }
    }

    private fun addView(uri: Uri) {
        attachPhotoIsClickable(false)
        val scale = requireActivity().resources.displayMetrics.density
        val pixels = (75 * scale + 0.5f).toInt()
        val params = LinearLayoutCompat.LayoutParams(pixels, pixels)

        when (null) {
            imageOneLocal -> {
                initViewImageOne(uri, params)
            }
            imageTwoLocal -> {
                initViewImageTwo(uri, params)
            }
            imageThreeLocal -> {
                initViewImageThree(uri, params)
            }
        }
    }

    private fun attachPhotoIsClickable(b: Boolean) {
        flagIsClickableAttachImage = b
    }

    private fun initViewImageOne(uri: Uri, params: LinearLayoutCompat.LayoutParams) {
        imageOneLocal = uri
        //listUriImage.add(0, imageOneLocal!!)
        viewModel.image(uri, 1)
        imageViewOne.layoutParams = params
        imageViewOne.visibility = View.GONE
        deleteImageOne.let {
            it.layoutParams =
                LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            it.gravity = Gravity.CENTER
            it.text = getText(R.string.delete_image)
            it.textSize = 12f
            it.setOnClickListener {
                linearLayoutOne.removeView(imageViewOne)
                imageViewOne.load(null)
                linearLayoutOne.removeView(deleteImageOne)
                deleteImageView(imageOneLocal!!, linearLayoutOne)
            }
        }
        linearLayoutOne.let {
            it.orientation = LinearLayoutCompat.VERTICAL
            it.addView(imageViewOne)
            it.addView(progressLoadingImage)
            it.addView(deleteImageOne)
        }
        binding.containerAddImagePublication.addView(linearLayoutOne)
    }

    private fun initViewImageTwo(uri: Uri, params: LinearLayoutCompat.LayoutParams) {
        imageTwoLocal = uri
        //listUriImage.add(1, imageTwoLocal!!)
        viewModel.image(uri, 2)
        imageViewTwo.layoutParams = params
        imageViewTwo.visibility = View.GONE
        deleteImageTwo.let {
            it.layoutParams =
                LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            it.gravity = Gravity.CENTER
            it.text = getText(R.string.delete_image)
            it.textSize = 12f
            it.setOnClickListener {
                linearLayoutTwo.removeView(imageViewTwo)
                imageViewTwo.load(null)
                linearLayoutTwo.removeView(deleteImageTwo)
                deleteImageView(imageTwoLocal!!, linearLayoutTwo)
            }
        }
        linearLayoutTwo.let {
            it.orientation = LinearLayoutCompat.VERTICAL
            it.addView(imageViewTwo)
            it.addView(progressLoadingImage)
            it.addView(deleteImageTwo)
        }
        binding.containerAddImagePublication.addView(linearLayoutTwo)
    }

    private fun initViewImageThree(uri: Uri, params: LinearLayoutCompat.LayoutParams) {
        imageThreeLocal = uri
        //listUriImage.add(2, imageThreeLocal!!)
        viewModel.image(uri, 3)
        imageViewThree.layoutParams = params
        imageViewThree.visibility = View.GONE
        deleteImageThree.let {
            it.layoutParams =
                LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            it.gravity = Gravity.CENTER
            it.text = getText(R.string.delete_image)
            it.textSize = 12f
            it.setOnClickListener {
                linearLayoutThree.removeView(imageViewThree)
                imageViewThree.load(null)
                linearLayoutThree.removeView(deleteImageThree)
                deleteImageView(imageThreeLocal!!, linearLayoutThree)
            }
        }
        linearLayoutThree.let {
            it.orientation = LinearLayoutCompat.VERTICAL
            it.addView(imageViewThree)
            it.addView(progressLoadingImage)
            it.addView(deleteImageThree)
        }
        binding.containerAddImagePublication.addView(linearLayoutThree)
    }

    private fun initViewProgressBar(l: LinearLayoutCompat) {

    }

    private fun deleteImageView(positionUri: Uri, linearLayoutCompat: LinearLayoutCompat) {
        when (positionUri) {
            imageOneLocal -> {
                imageOneLocal = null
            }
            imageTwoLocal -> {
                imageTwoLocal = null
            }
            imageThreeLocal -> {
                imageThreeLocal = null
            }
        }
        //listUriImage.remove(positionUri)
        binding.containerAddImagePublication.removeView(linearLayoutCompat)
        viewModel.deleteImage(positionUri)
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
            loadUriOneImage.observe(viewLifecycleOwner) {
                initImagePublication(it, 1)
            }

            loadUriTwoImage.observe(viewLifecycleOwner) {
                initImagePublication(it, 2)
            }
            loadUriThreeImage.observe(viewLifecycleOwner) {
                initImagePublication(it, 3)
            }
            progressLoad.observe(viewLifecycleOwner) {
                initViewProgressBar(linearLayoutOne)
//                with(binding) {
//                    progressBarImagePostAddPost.visibility = View.VISIBLE
//                    textViewProgress.visibility = View.VISIBLE
//                    textViewProgress.text = "$it%"
//                }
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
                    if (flagIsClickableAttachImage) {
                        resultLauncher.launch("image/*")
                    } else {
                        showToast("Идет загрузка изображения")
                    }
                } else {
                    showToast("Лимит привышен")
                }
            }
        }
    }

    private fun showToast(it: String?) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun initImagePublication(it: Uri?, numberImage: Int) {
        attachPhotoIsClickable(true)
        when (numberImage) {
            1 -> {
                imageOne = it
                imageViewOne.load(it)
                imageViewOne.visibility = View.VISIBLE
                linearLayoutOne.removeView(progressLoadingImage)
            }
            2 -> {
                imageTwo = it
                imageViewTwo.load(it)
                imageViewTwo.visibility = View.VISIBLE
                linearLayoutTwo.removeView(progressLoadingImage)
            }
            3 -> {
                imageThree = it
                imageViewThree.load(it)
                imageViewThree.visibility = View.VISIBLE
                linearLayoutThree.removeView(progressLoadingImage)
            }
        }
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
                //viewModel.deleteImage()
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

    override fun onDetach() {
        super.onDetach()
        locationManager.removeUpdates(locationListener)
        viewModel.deleteImage(imageOneLocal, imageTwoLocal, imageThreeLocal)
    }
}
