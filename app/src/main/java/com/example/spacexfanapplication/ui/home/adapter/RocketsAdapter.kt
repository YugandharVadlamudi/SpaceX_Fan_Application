package com.example.spacexfanapplication.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacexfanapplication.R
import com.example.spacexfanapplication.databinding.InflateRocketsRvItemBinding
import com.example.spacexfanapplication.ui.home.model.LaunchDetailsResponse

class RocketsAdapter : RecyclerView.Adapter<RocketsAdapter.RocketsViewHolder>() {
    private val rocketsList: ArrayList<LaunchDetailsResponse?> = ArrayList()
    private var clickListener: ClickListener? = null
    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    inner class RocketsViewHolder(private val viewDataBinding: InflateRocketsRvItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun onBind(launchDetailsResponse: LaunchDetailsResponse?) {
            launchDetailsResponse?.let { data ->
                viewDataBinding.imgThumbnail.let { img ->
                    Glide.with(img.context)
                        .load(data.links?.patch?.small)
                        .placeholder(R.drawable.no_image_16_9)
                        .into(img)
                }
                viewDataBinding.txtItemHeading.text = data.name
                viewDataBinding.txtItemDescription.text = data.details
                when (data.isFav) {
                    true -> {
                        viewDataBinding.iconFav.let { img ->
                            Glide.with(img.context)
                                .load(R.drawable.ic_favorite)
                                .into(img)
                        }
                    }
                    false -> {
                        viewDataBinding.iconFav.let { img ->
                            Glide.with(img.context)
                                .load(R.drawable.ic_un_favorite)
                                .into(img)
                        }
                    }
                }

                if (launchDetailsResponse == rocketsList.last()) {
                    viewDataBinding.viewUnderlineItem.visibility = View.INVISIBLE
                } else {
                    viewDataBinding.viewUnderlineItem.visibility = View.VISIBLE
                }
                viewDataBinding.iconFav.setOnClickListener {
                    if (data.isFav) {
                        clickListener?.removeFav(data)
                    } else {
                        clickListener?.addFav(data)
                    }
                }
                viewDataBinding.clRvItemRoot.setOnClickListener {
                    clickListener?.onItemClick(data)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder {
        val inflateRocketsRvItemBinding = DataBindingUtil.bind<ViewDataBinding>(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.inflate_rockets_rv_item, parent, false)
        ) as InflateRocketsRvItemBinding
        return RocketsViewHolder(inflateRocketsRvItemBinding)
    }

    override fun onBindViewHolder(holder: RocketsViewHolder, position: Int) {
        holder.onBind(rocketsList[position])
    }

    override fun getItemCount(): Int {
        return rocketsList.size
    }

    fun addItems(rocketsList: ArrayList<LaunchDetailsResponse?>) {
        clearList()
        this.rocketsList.addAll(rocketsList)
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun addFav(data: LaunchDetailsResponse)
        fun removeFav(data: LaunchDetailsResponse)
        fun onItemClick(data: LaunchDetailsResponse)
    }

     fun clearList() {
        rocketsList.clear()
        notifyDataSetChanged()
    }

}