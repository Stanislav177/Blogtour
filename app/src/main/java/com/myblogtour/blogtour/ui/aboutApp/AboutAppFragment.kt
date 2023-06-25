package com.myblogtour.blogtour.ui.aboutApp

import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.BuildConfig
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentAboutAppBinding
import com.myblogtour.blogtour.ui.privacyPolicy.DialogPolicy
import com.myblogtour.blogtour.ui.privacyPolicy.POLITIC
import com.myblogtour.blogtour.ui.privacyPolicy.TERMS
import com.myblogtour.blogtour.utils.BaseFragment

class AboutAppFragment : BaseFragment<FragmentAboutAppBinding>(FragmentAboutAppBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textViewVersionApp.text = "${requireActivity().resources.getText(R.string.version) } ${BuildConfig.VERSION_NAME }"
            termsOfUse.setOnClickListener {
                openDialog(TERMS)
            }
            politicConf.setOnClickListener {
                openDialog(POLITIC)
            }
        }
    }

    private fun openDialog(status:Int) {
        val openDialog = DialogPolicy.newInstance(status)
        openDialog.show(requireActivity().supportFragmentManager, "")
    }
}