package com.myblogtour.blogtour.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.myblogtour.blogtour.databinding.ItemImageCarouselBinding
import com.myblogtour.blogtour.domain.ImageEntity

class HomeImagePublicationRecyclerAdapter :
    RecyclerView.Adapter<HomeImagePublicationRecyclerAdapter.ViewHolderImagePublication>() {

    private var listImagePublication: List<ImageEntity> = listOf()

    fun setListImagePublication(listImage: List<ImageEntity>) {
        this.listImagePublication = listImage
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderImagePublication {
        val itemBinding: ItemImageCarouselBinding =
            ItemImageCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderImagePublication(itemBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolderImagePublication, position: Int) {
        holder.bind(this.listImagePublication[position], itemCount)
    }

    override fun getItemCount() = listImagePublication.size

    inner class ViewHolderImagePublication(view: View) : ViewHolder(view) {
        fun bind(image: ImageEntity, size: Int) {
            ItemImageCarouselBinding.bind(itemView).apply {
                imagePublication.load(image.url)
                val layoutPosition = layoutPosition + 1
                val l = layoutPosition
            }
        }
    }
}