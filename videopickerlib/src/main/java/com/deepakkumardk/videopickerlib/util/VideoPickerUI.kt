package com.deepakkumardk.videopickerlib.util

import com.deepakkumardk.videopickerlib.model.VideoPickerItem

object VideoPickerUI {
    private lateinit var videoPickerItem: VideoPickerItem

    fun getPickerItem() = videoPickerItem

    fun setPickerUI(videoPickerItem: VideoPickerItem) {
        this.videoPickerItem = videoPickerItem
    }

    fun getDebugMode() = videoPickerItem.debugMode

    fun getTheme() = videoPickerItem.themeResId

}