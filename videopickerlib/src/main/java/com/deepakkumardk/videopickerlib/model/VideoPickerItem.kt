package com.deepakkumardk.videopickerlib.model

import java.util.concurrent.TimeUnit


class VideoPickerItem {
    var debugMode = false
    var themeResId: Int? = null

    var showIcon = false
    var timeLimit: Long = TimeUnit.MINUTES.toMillis(1)
    var sizeLimit: Long = 100*1024
}