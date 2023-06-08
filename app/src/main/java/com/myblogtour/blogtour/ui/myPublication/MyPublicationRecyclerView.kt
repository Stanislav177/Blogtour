package com.myblogtour.blogtour.ui.myPublication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.myblogtour.blogtour.databinding.ItemRecyclerViewMyPublicationBinding
import com.myblogtour.blogtour.domain.PublicationEntity

class MyPublicationRecyclerView(val myOnItemClickListener: MyOnItemClickListener) :
    RecyclerView.Adapter<MyPublicationRecyclerView.ViewHolderMyPublication>() {

    private var myPublicationList: MutableList<PublicationEntity> = mutableListOf()


    fun setMyPublicationList(listMyPublication: List<PublicationEntity>) {
        this.myPublicationList = listMyPublication.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMyPublication {
        val itemBinding: ItemRecyclerViewMyPublicationBinding =
            ItemRecyclerViewMyPublicationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolderMyPublication(itemBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolderMyPublication, position: Int) {
        holder.bind(myPublicationList[position])
    }

    override fun getItemCount() = myPublicationList.size

    inner class ViewHolderMyPublication(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(publication: PublicationEntity) {
            val adapter: MyPublicationImageCarouselAdapter by lazy { MyPublicationImageCarouselAdapter() }

            val imageSize = publication.urlImage.size

            ItemRecyclerViewMyPublicationBinding.bind(itemView).run {
                if (imageSize != 0) {
                    imageMyPublicationVP.adapter = adapter
                    if (imageSize > 1) {
                        adapter.setImageList(publication.urlImage)
                        TabLayoutMediator(tabLayoutMyPublication, imageMyPublicationVP) { _, _ ->
                            imageMyPublicationVP.apply {
                                clipChildren = false
                                clipToPadding = false
                                (getChildAt(0) as RecyclerView).overScrollMode =
                                    RecyclerView.OVER_SCROLL_NEVER
                            }
                        }.attach()
                    } else {
                        adapter.setImageList(publication.urlImage)
                    }
                }
                textViewDatePublication.text = publication.date
                textViewMyPublication.text = publication.text
                textViewCounterLikeMyPublication.text = " - ${publication.counterLike}"
                btnDeletePublication.setOnClickListener {
                    myOnItemClickListener.onClickDeletePublication(publication.id,
                        publication.idCounterLike)
                    myPublicationList.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
            }
        }
    }
}