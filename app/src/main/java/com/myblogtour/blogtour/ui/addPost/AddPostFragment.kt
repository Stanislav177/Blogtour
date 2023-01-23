package com.myblogtour.blogtour.ui.addPost

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.myblogtour.blogtour.databinding.FragmentAddPostBinding
import com.myblogtour.blogtour.utils.BaseFragment

class AddPostFragment : BaseFragment<FragmentAddPostBinding>(FragmentAddPostBinding::inflate) {

    private val viewModel: AddPostViewModel by lazy {
        ViewModelProvider(this)[AddPostViewModel::class.java]
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                viewModel.image(it)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.publishPostLiveData.observe(viewLifecycleOwner) {
            publishPost(it)
        }
        viewModel.loadUri.observe(viewLifecycleOwner){
            binding.imagePostAddPost.load(it)
        }
        with(binding) {
            publishBtnAddPost.setOnClickListener {
                //viewModel.dataPost(editTextPost.text.toString(), editTextLocation.text.toString())
            }
            attachPhotoAddPost.setOnClickListener {
                resultLauncher.launch("image/*")
            }
        }
    }

    private fun publishPost(it: Boolean) {
        if (it) {
            Toast.makeText(requireContext(), "Пост размещен", Toast.LENGTH_SHORT).show()
        }

    }

}