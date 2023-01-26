package com.myblogtour.blogtour.ui.addPost

import android.os.Bundle
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
        viewModel.loadUri.observe(viewLifecycleOwner) {
            with(binding) {
                if (it != null) {
                    progressBarImagePostAddPost.visibility = View.GONE
                    textViewProgress.visibility = View.GONE
                    imagePostAddPost.load(it)
                    cancelImage.visibility = View.VISIBLE
                } else{
                    imagePostAddPost.load(null)
                    cancelImage.visibility = View.GONE
                }

            }
        }
        viewModel.progressLoad.observe(viewLifecycleOwner) {
            with(binding) {
                progressBarImagePostAddPost.visibility = View.VISIBLE
                textViewProgress.visibility = View.VISIBLE
                textViewProgress.text = "$it%"
            }
        }
        with(binding) {
            publishBtnAddPost.setOnClickListener {
                //viewModel.dataPost(editTextPost.text.toString(), editTextLocation.text.toString())
            }
            attachPhotoAddPost.setOnClickListener {
                resultLauncher.launch("image/*")
            }
            cancelImage.setOnClickListener {
                viewModel.deleteImage()
            }
        }
    }

    private fun publishPost(it: Boolean) {
        if (it) {
            Toast.makeText(requireContext(), "Пост размещен", Toast.LENGTH_SHORT).show()
        }

    }

}