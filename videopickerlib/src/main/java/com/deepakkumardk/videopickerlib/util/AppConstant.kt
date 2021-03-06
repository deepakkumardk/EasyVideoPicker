package com.deepakkumardk.videopickerlib.util

/**
 * @author Deepak Kumar
 * @since 12/10/19
 */
import android.provider.MediaStore

const val RC_READ_STORAGE = 3000

const val EXTRA_SELECTED_VIDEOS = "selected_videos"

const val VIDEO_ID = MediaStore.Video.Media._ID
const val VIDEO_DATA = MediaStore.Video.Media.DATA
const val VIDEO_DURATION = MediaStore.Video.VideoColumns.DURATION
const val VIDEO_SIZE = MediaStore.Video.Media.SIZE
const val ORDER_BY = MediaStore.Video.Media.DATE_TAKEN
const val VIDEO_DISPLAY_NAME = MediaStore.Video.Media.BUCKET_DISPLAY_NAME
