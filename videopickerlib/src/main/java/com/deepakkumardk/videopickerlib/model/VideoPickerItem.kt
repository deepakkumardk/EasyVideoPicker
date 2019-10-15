package com.deepakkumardk.videopickerlib.model

/**
 * @author Deepak Kumar
 * @since 12/10/19
 */
class VideoPickerItem {
    var debugMode = false
    var themeResId: Int? = null

    var showIcon = false
    var showDuration = false
    var timeLimit: Long? = null
    var sizeLimit: Long? = null

    var selectionMode : SelectionMode = SelectionMode.Multiple
    var selectionStyle : SelectionStyle = SelectionStyle.Small
}