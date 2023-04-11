package com.myblogtour.blogtour.ui.noNetworkConnection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.myblogtour.blogtour.appState.AppStateNetworkConnection
import com.myblogtour.blogtour.databinding.BottomDialogNoNetworkBinding
import com.myblogtour.blogtour.utils.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoNetworkConnectionDialog :
    BaseDialogFragment<BottomDialogNoNetworkBinding>(BottomDialogNoNetworkBinding::inflate) {

    private val vm: NoNetworkConnectionViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        vm.getLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is AppStateNetworkConnection.OnConnectionNetwork -> {
                    if (it.connectionNetwork.b) {
                        dismiss()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Проверьте сетевые настройки",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        with(binding) {
                            progressBarUpdateNetwork.visibility = View.GONE
                            btnUpdateNetwork.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        binding.btnUpdateNetwork.setOnClickListener {
            updateNetwork()
        }
    }

    private fun updateNetwork() {
        with(binding) {
            btnUpdateNetwork.visibility = View.GONE
            progressBarUpdateNetwork.visibility = View.VISIBLE
        }
        vm.updateNetworkConnection()
    }
}