package com.myblogtour.blogtour.ui.myPublication

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.myblogtour.blogtour.databinding.FragmentMyPublicationBinding
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.utils.BaseFragment

class MyPublicationFragment(private val uidUser: String) :
    BaseFragment<FragmentMyPublicationBinding>(FragmentMyPublicationBinding::inflate),
    MyOnItemClickListener {

    private val viewModel: MyPublicationViewModel by lazy {
        ViewModelProvider(this)[MyPublicationViewModel::class.java]
    }

    private val recyclerViewMyPublication: MyPublicationRecyclerView by lazy {
        MyPublicationRecyclerView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listPublication.observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.recyclerViewMyPublication.adapter = recyclerViewMyPublication
        viewModel.getMyPublication(uidUser)
    }

    private fun renderData(it: List<PublicationEntity>?) {
        it?.let {
            recyclerViewMyPublication.setMyPublicationList(it)
        }
    }

    override fun onClickDeletePublication(idPublication: String) {
        val id = idPublication
    }

}