package com.deepakkumardk.videopickerlib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Deepak Kumar
 * @since 12/10/19
 */
@Parcelize
data class VideoModel(
    var id: String? = null,
    var folderName: String? = null,
    var videoPath: String? = null,
    var videoDuration: Long? = null,
    var isSelected: Boolean = false
) : Parcelable