package com.deepakkumardk.videopickerlib

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.deepakkumardk.videopickerlib.model.SelectionMode
import com.deepakkumardk.videopickerlib.model.SelectionStyle
import com.deepakkumardk.videopickerlib.model.VideoModel
import com.deepakkumardk.videopickerlib.util.*
import java.io.File

/**
 * @author Deepak Kumar
 * @since 12/10/19
 */
class VideoPickerAdapter(
    private val itemList: MutableList<VideoModel>,
    val listener: (VideoModel, Int, View, View) -> Unit
) : RecyclerView.Adapter<VideoPickerAdapter.VideoViewHolder>() {

    private var selectionStyle: SelectionStyle = VideoPickerUI.getPickerItem().selectionStyle
    private val placeholder = VideoPickerUI.getPickerItem().placeholder

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(itemView)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val context = holder.itemView.context
        val model = itemList[position]

        Glide.with(context)
            .load(File(model.videoPath))
            .placeholder(placeholder)
            .fallback(placeholder)
            .error(placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.videoImage)

        if (VideoPickerUI.getShowIcon()) {
            holder.imageSmallIcon.show()
            val ex = getMimeType(model.videoPath!!)
            if (ex == "gif") {  //Will not work as gif are stored in MediaStore.Images.Media
                Glide.with(context)
                    .load(R.drawable.ic_gif)
                    .into(holder.imageSmallIcon)
            } else {
                Glide.with(context)
                    .load(R.drawable.ic_video)
                    .into(holder.imageSmallIcon)
            }
        } else {
            holder.imageSmallIcon.hide()
        }

        holder.opacityView.isSelected = model.isSelected
        when (selectionStyle) {
            is SelectionStyle.Small -> {
                holder.imageCheck.apply {
                    show()
                    isSelected = model.isSelected
                }
            }
            is SelectionStyle.Large -> {
                holder.imageCheckCenter.apply {
                    show()
                    isSelected = model.isSelected
                }
            }

        }
        if (VideoPickerUI.getSelectionMode() == SelectionMode.Single) {
            holder.imageCheck.hide()
        }
        if (VideoPickerUI.getPickerItem().showDuration) {
            holder.textDuration.show()
            holder.textDuration.text = model.videoDuration?.toDuration()
        } else {
            holder.textDuration.hide()
        }

        holder.itemView.setOnClickListener {
            when (selectionStyle) {
                is SelectionStyle.Small -> listener(
                    model, holder.adapterPosition, holder.imageCheck, holder.opacityView
                )
                is SelectionStyle.Large -> listener(
                    model, holder.adapterPosition, holder.imageCheckCenter, holder.opacityView
                )
            }
        }

    }


    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoImage: ImageView = itemView.findViewById(R.id.video_thumbnail)
        val opacityView: View = itemView.findViewById(R.id.image_opacity)
        val imageCheck: ImageView = itemView.findViewById(R.id.image_check)
        val imageCheckCenter: ImageView = itemView.findViewById(R.id.image_check_center)
        val imageSmallIcon: ImageView = itemView.findViewById(R.id.small_icon)
        val textDuration: TextView = itemView.findViewById(R.id.text_duration)
    }
}