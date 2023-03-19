package com.myblogtour.blogtour.ui.search

import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.databinding.FragmentResultSearchBinding
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultSearchFragment :
    BaseFragment<FragmentResultSearchBinding>(FragmentResultSearchBinding::inflate) {

    private val viewModelResult: ResultSearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelResult.getSearchPublication("location=\"Ногинск\"")
    }


}