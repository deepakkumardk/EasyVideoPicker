package com.deepakkumardk.videopickerlib.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap

/**
 * @author Deepak Kumar
 * @since 12/10/19
 */
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

fun getMimeType(fileUrl: String): String? {
    val extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
}

fun Context.getDuration(path: String): Long {
    val retriever = MediaMetadataRetriever()
    //use one of overloaded setDataSource() functions to set your data source
    retriever.setDataSource(this, Uri.parse(path))
    val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    val timeInMilli = time.toLong()

    retriever.release()
    return timeInMilli
}

fun log(message: String = "") = Log.d("TAG_VIDEO_PICKER", message)
