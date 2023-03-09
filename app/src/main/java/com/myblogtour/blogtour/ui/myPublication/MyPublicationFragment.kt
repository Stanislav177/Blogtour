package com.myblogtour.blogtour.ui.myPublication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.myblogtour.blogtour.databinding.FragmentMyPublicationBinding
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPublicationFragment :
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
        binding.progressBarMyPublication.visibility = View.VISIBLE
        binding.recyclerViewMyPublication.adapter = recyclerViewMyPublication
        viewModelMyPublication.getMyPublication()
    }

    private fun renderData(it: List<PublicationEntity>?) {
        var sizePublication = 0
        it?.apply {
            sizePublication = this.size
        }
        if (sizePublication > 0) {
            it?.let {
                binding.progressBarMyPublication.visibility = View.GONE
                recyclerViewMyPublication.setMyPublicationList(it)
            }
        } else {
            binding.progressBarMyPublication.visibility = View.GONE
            Toast.makeText(requireActivity(), "Данных нет", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClickDeletePublication(idPublication: String, idLike: String) {
        viewModelMyPublication.deletePublication(idPublication, idLike)
    }
}