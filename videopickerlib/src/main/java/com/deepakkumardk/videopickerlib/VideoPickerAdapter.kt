package com.deepakkumardk.videopickerlib

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.deepakkumardk.videopickerlib.model.VideoModel

class VideoPickerAdapter(
    private val itemList: MutableList<VideoModel>,
    val listener: (VideoModel, Int, View) -> Unit
) : RecyclerView.Adapter<VideoPickerAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(itemView)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val model = itemList[position]

        Glide.with(holder.itemView.context)
            .load(model.photoUri)
            .placeholder(R.drawable.ic_image_placeholder)
            .fallback(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_placeholder)
            .into(holder.videoImage)

        holder.videoImage.setOnClickListener { listener(model, position, it) }

    }


    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoImage: ImageView = itemView.findViewById(R.id.video_thumbnail)
    }
}