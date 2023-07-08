package com.myblogtour.blogtour.ui.maps.dialogLocationMap

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.myblogtour.blogtour.databinding.DialogCustomMapLocationBinding
import com.myblogtour.blogtour.ui.maps.data.EntityAddress
import com.myblogtour.blogtour.ui.maps.observable.Observable
import com.myblogtour.blogtour.utils.BaseBottomSheetDialogFragment
import org.koin.android.ext.android.inject

class DialogLocationMap :
    BaseBottomSheetDialogFragment<DialogCustomMapLocationBinding>(DialogCustomMapLocationBinding::inflate) {
    private var entityAddress: EntityAddress? = null
    private var address: String? = null
    private var lon: Double? = null
    private var lat: Double? = null

    private val obsImpl:Observable by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        address = arguments?.getString("ADDRESS")
        lon = arguments?.getDouble("LON")
        lat = arguments?.getDouble("LAT")

        entityAddress = EntityAddress(address, lon!!, lat!!)
        binding.locationMap.text = address

        onClick()
    }

    private fun onClick() {
        binding.btnOkLocation.setOnClickListener {
            obsImpl.sendUpdateEvent(entityAddress!!)
            dismiss()
            requireActivity().supportFragmentManager.popBackStack("ADD",
                FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    companion object {
        fun newInstance(address: String?, lat: Double, lon: Double) = DialogLocationMap().apply {
            arguments = Bundle().apply {
                putDouble("LAT", lat)
                putDouble("LON", lon)
                putString("ADDRESS", address)
            }
        }
    }
}