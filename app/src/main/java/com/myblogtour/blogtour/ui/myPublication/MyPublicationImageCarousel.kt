package com.myblogtour.blogtour.ui.myPublication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.myblogtour.blogtour.databinding.ItemImageCarouselBinding
import com.myblogtour.blogtour.databinding.ItemImageCarouselMyPublicationBinding
import com.myblogtour.blogtour.domain.ImageEntity

class MyPublicationImageCarousel :
    RecyclerView.Adapter<MyPublicationImageCarousel.ViewHolderCarouselImageMyPublication>() {

    private var listImage: List<ImageEntity> = listOf()

    fun setImageList(list: List<ImageEntity>) {
        this.listImage = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolderCarouselImageMyPublication {
        val itemImage =
            ItemImageCarouselMyPublicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCarouselImageMyPublication(itemImage.root)
    }

    override fun onBindViewHolder(holder: ViewHolderCarouselImageMyPublication, position: Int) {
        holder.bind(listImage[position])
    }

    override fun getItemCount() = listImage.size

    inner class ViewHolderCarouselImageMyPublication(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(image: ImageEntity) {
            ItemImageCarouselMyPublicationBinding.bind(itemView).apply {
                imagePublication.load(image.url)
            }
        }
    }
}