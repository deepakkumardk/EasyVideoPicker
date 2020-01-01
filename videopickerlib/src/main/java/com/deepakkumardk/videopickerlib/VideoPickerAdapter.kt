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
import com.deepakkumardk.videopickerlib.util.VideoPickerUI
import com.deepakkumardk.videopickerlib.util.getMimeType
import com.deepakkumardk.videopickerlib.util.hide
import com.deepakkumardk.videopickerlib.util.show
import com.deepakkumardk.videopickerlib.util.toDuration
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
        holder.bind(itemList[position])
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoImage: ImageView = itemView.findViewById(R.id.video_thumbnail)
        private val opacityView: View = itemView.findViewById(R.id.image_opacity)
        private val imageCheck: ImageView = itemView.findViewById(R.id.image_check)
        private val imageCheckCenter: ImageView = itemView.findViewById(R.id.image_check_center)
        private val imageSmallIcon: ImageView = itemView.findViewById(R.id.small_icon)
        private val textDuration: TextView = itemView.findViewById(R.id.text_duration)

        init {
            itemView.setOnClickListener {
                when (selectionStyle) {
                    is SelectionStyle.Small -> listener(
                        itemList[adapterPosition], adapterPosition, imageCheck, opacityView
                    )
                    is SelectionStyle.Large -> listener(
                        itemList[adapterPosition], adapterPosition, imageCheckCenter, opacityView
                    )
                }
            }
        }

        fun bind(model: VideoModel) {
            val context = itemView.context

            Glide.with(context)
                .load(File(model.videoPath))
                .placeholder(placeholder)
                .fallback(placeholder)
                .error(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(videoImage)

            if (VideoPickerUI.getShowIcon()) {
                imageSmallIcon.show()
                val ex = getMimeType(model.videoPath!!)
                if (ex == "gif") {  //Will not work as gif are stored in MediaStore.Images.Media
                    Glide.with(context)
                        .load(R.drawable.ic_gif)
                        .into(imageSmallIcon)
                } else {
                    Glide.with(context)
                        .load(R.drawable.ic_video)
                        .into(imageSmallIcon)
                }
            } else {
                imageSmallIcon.hide()
            }

            opacityView.isSelected = model.isSelected
            when (selectionStyle) {
                is SelectionStyle.Small -> {
                    imageCheck.show()
                    imageCheck.isSelected = model.isSelected
                }
                is SelectionStyle.Large -> {
                    imageCheckCenter.show()
                    imageCheckCenter.isSelected = model.isSelected
                }

            }
            if (VideoPickerUI.getSelectionMode() == SelectionMode.Single) {
                imageCheck.hide()
            }
            if (VideoPickerUI.getPickerItem().showDuration) {
                textDuration.show()
                textDuration.text = model.videoDuration?.toDuration()
            } else {
                textDuration.hide()
            }
        }
    }
}