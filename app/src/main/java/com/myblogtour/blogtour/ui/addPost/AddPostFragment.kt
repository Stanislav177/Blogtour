package com.myblogtour.blogtour.ui.addPost

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.myblogtour.blogtour.databinding.FragmentAddPostBinding
import com.myblogtour.blogtour.utils.BaseFragment

class AddPostFragment : BaseFragment<FragmentAddPostBinding>(FragmentAddPostBinding::inflate) {

    private val viewModel: AddPostViewModel by lazy {
        ViewModelProvider(this)[AddPostViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.publishPostLiveData.observe(viewLifecycleOwner) {
            publishPOst(it)
        }
        with(binding){
            publishBtnAddPost.setOnClickListener {
                viewModel.dataPost(editTextPost.text.toString(),editTextLocation.text.toString())
            }
        }
    }

    private fun publishPOst(it: Boolean) {
        if(it){
            Toast.makeText(requireContext(), "Пост размещен", Toast.LENGTH_SHORT).show()
        }

    }

}