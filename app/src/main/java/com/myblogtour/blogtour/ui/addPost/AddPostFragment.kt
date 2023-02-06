package com.myblogtour.blogtour.ui.addPost

import android.net.Uri
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

    lateinit var imageUri: Uri

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
            initImagePost(it)
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
                viewModel.dataPost(editTextPost.text.toString(), editTextLocation.text.toString(), imageUri)
            }
            attachPhotoAddPost.setOnClickListener {
                resultLauncher.launch("image/*")
            }
            deleteImagePublication.setOnClickListener {
                viewModel.deleteImage()
            }
        }
    }

    private fun initImagePost(it: Uri?) {
        with(binding) {
            if (it != null) {
                imageUri = it
                progressBarImagePostAddPost.visibility = View.GONE
                textViewProgress.visibility = View.GONE
                imagePostAddPost.load(it)
                deleteImagePublication.visibility = View.VISIBLE
            } else {
                imagePostAddPost.load(null)
                deleteImagePublication.visibility = View.GONE
            }
        }
    }

    private fun publishPost(it: Boolean) {
        if (it) {
            Toast.makeText(requireContext(), "Пост размещен", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.deleteImageDetach()
    }
}