package com.deepakkumardk.videopickerlib.util

import android.app.Activity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import java.util.concurrent.TimeUnit

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

fun Activity?.applyCustomTheme(themeId: Int?) {
    themeId?.let {
        this?.setTheme(themeId)
    }
}

fun getMimeType(fileUrl: String): String? {
    val extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
}

fun Long?.toDuration(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this!!).addLeadingZero()
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % TimeUnit.HOURS.toMinutes(1)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % TimeUnit.MINUTES.toSeconds(1)
    val duration = String.format("%02d:%02d", minutes, seconds)
    return if (hours != "00") "$hours:$duration" else duration
}

fun Long.addLeadingZero(): String {
    return String.format("%02d", this)
}

fun log(message: String = "") = Log.d("TAG_VIDEO_PICKER", message)
