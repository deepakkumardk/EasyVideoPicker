package com.deepakkumardk.videopickerlib.model

class VideoPickerItem {
    var debugMode = false
    var themeResId: Int? = null

    var showIcon = false
    var timeLimit: Long? = null
    var sizeLimit: Long? = null

    var selectionMode : SelectionMode = SelectionMode.Multiple
}