package com.myblogtour.blogtour.ui.addPublication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.myblogtour.blogtour.databinding.ItemLoadingImagePublicationBinding
import com.myblogtour.blogtour.domain.ImagePublicationEntity

class AddPublicationImageAdapter :
    RecyclerView.Adapter<AddPublicationImageAdapter.ViewHolderImage>() {

    private var listImagePublication: MutableList<ImagePublicationEntity> = mutableListOf()

    fun setImageList(imagePublication: ImagePublicationEntity) {
        this.listImagePublication.add(imagePublication)
        notifyDataSetChanged()
    }

    fun replaceImage(imagePublication: ImagePublicationEntity) {
        val positionListImage = listImagePublication.indices.find {
            listImagePublication[it].uriLocal == imagePublication.uriLocal
        }
        this.listImagePublication.filter { it.uriLocal == imagePublication.uriLocal }
            .forEach {
                it.url = imagePublication.url
                it.uriLocal = imagePublication.uriLocal
                it.loading = imagePublication.loading
                it.progress = imagePublication.progress
            }
        positionListImage?.let {
            notifyItemChanged(it)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderImage {
        val itemBinding: ItemLoadingImagePublicationBinding =
            ItemLoadingImagePublicationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        return ViewHolderImage(itemBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolderImage, position: Int) {
        holder.bind(listImagePublication[position])
    }

    override fun getItemCount() = listImagePublication.size

    inner class ViewHolderImage(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(image: ImagePublicationEntity) {
            ItemLoadingImagePublicationBinding.bind(itemView).apply {
                if (image.loading) {
                    progressBarLoadingImage.visibility = View.VISIBLE
                    progressLoadingImage.visibility = View.VISIBLE
                    progressLoadingImage.text = "${image.progress}%"
                    imagePublication.visibility = View.GONE
                } else {
                    progressBarLoadingImage.visibility = View.GONE
                    progressLoadingImage.visibility = View.GONE
                    imagePublication.visibility = View.VISIBLE
                    imagePublication.load(image.uriLocal)
                }
            }
        }
    }
}