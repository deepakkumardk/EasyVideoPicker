package com.deepakkumardk.videopickerlib.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoModel(
    var id: String? = null,
    var folderName: String? = null,
    var videoPath: String? = null,
    var isSelected: Boolean = false,
    var photoUri: Uri? = null
) : Parcelable