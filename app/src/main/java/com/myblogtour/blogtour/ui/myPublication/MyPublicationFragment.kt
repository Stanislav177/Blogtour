package com.myblogtour.blogtour.ui.myPublication

import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.databinding.FragmentMyPublicationBinding
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPublicationFragment(private val uidUser: String) :
    BaseFragment<FragmentMyPublicationBinding>(FragmentMyPublicationBinding::inflate),
    MyOnItemClickListener {

    private val viewModelMyPublication: MyPublicationViewModel by viewModel()

    private val recyclerViewMyPublication: MyPublicationRecyclerView by lazy {
        MyPublicationRecyclerView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelMyPublication.listPublication.observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.recyclerViewMyPublication.adapter = recyclerViewMyPublication
        viewModelMyPublication.getMyPublication(uidUser)
    }

    private fun renderData(it: List<PublicationEntity>?) {
        it?.let {
            recyclerViewMyPublication.setMyPublicationList(it)
        }
    }

    override fun onClickDeletePublication(idPublication: String, idLike: String) {
        viewModelMyPublication.deletePublication(idPublication, idLike)
    }

}