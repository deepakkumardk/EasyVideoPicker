package com.deepakkumardk.videopickerlib.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.hide() {
    this?.visibility = View.GONE
}

fun RecyclerView.init(context: Context) {
    this.apply {
        hasFixedSize()
        layoutManager = GridLayoutManager(context, 3)
    }
}

fun getVideoThumbUri(videoPath: String): Bitmap? {
    return ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MICRO_KIND)
}

fun Activity?.applyCustomTheme(themeId: Int?) {
    themeId?.let {
        this?.setTheme(themeId)
    }
}

fun log(message: String = "") = Log.d("TAG_VIDEO_PICKER", message)
