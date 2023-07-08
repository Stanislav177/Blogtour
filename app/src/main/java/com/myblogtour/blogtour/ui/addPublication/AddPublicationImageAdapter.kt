package com.myblogtour.blogtour.ui.addPublication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.myblogtour.blogtour.databinding.ItemLoadingImagePublicationBinding
import com.myblogtour.blogtour.domain.ImagePublicationEntity

class AddPublicationImageAdapter(private val onClickListenerPosition: MyOnClickListenerPosition) :
    RecyclerView.Adapter<AddPublicationImageAdapter.ViewHolderImage>() {

    private var listImagePublication: MutableList<ImagePublicationEntity> = mutableListOf()

    fun setImageList(imagePublication: ImagePublicationEntity) {
        this.listImagePublication.add(imagePublication)
        notifyDataSetChanged()
    }

    fun clearImageList(){
        this.listImagePublication.clear()
        notifyDataSetChanged()
    }

    fun replaceImage(imagePublication: ImagePublicationEntity) {
        listImagePublication.indices.find {
            listImagePublication[it].uriLocal == imagePublication.uriLocal
        }?.let { index ->
            listImagePublication[index].url = imagePublication.url
            listImagePublication[index].loading = imagePublication.loading
            listImagePublication[index].progress = imagePublication.progress
            notifyItemChanged(index)
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
                    cancelImagePublication.visibility = View.VISIBLE
                    cancelImagePublication.setOnClickListener {
                        onClickListenerPosition.onItemClickCancel()
                        listImagePublication.removeAt(layoutPosition)
                        notifyDataSetChanged()
                    }
                } else {
                    progressBarLoadingImage.visibility = View.GONE
                    progressLoadingImage.visibility = View.GONE
                    imagePublication.visibility = View.VISIBLE
                    imagePublication.load(image.uriLocal)
                    cancelImagePublication.visibility = View.GONE
                    deleteImagePublication.visibility = View.VISIBLE
                    deleteImagePublication.setOnClickListener {
                        onClickListenerPosition.onItemClick(image.uriLocal!!)
                        listImagePublication.removeAt(layoutPosition)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}