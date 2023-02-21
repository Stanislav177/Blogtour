package com.myblogtour.blogtour.ui.myPublication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myblogtour.blogtour.R

class MyPublicationFragment : Fragment() {

    companion object {
        fun newInstance() = MyPublicationFragment()
    }

    private lateinit var viewModel: MyPublicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_publication, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPublicationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}