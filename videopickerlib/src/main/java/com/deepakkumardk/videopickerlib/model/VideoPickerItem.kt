package com.deepakkumardk.videopickerlib.model

import android.support.annotation.DrawableRes
import com.deepakkumardk.videopickerlib.R

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
    @DrawableRes
    var placeholder: Int = R.drawable.ic_image_placeholder
    var limitMessage = "You can't select more than %s videos"
    var gridDecoration: Triple<Int, Int, Boolean> = Triple(3, 5, false)

    var selectionMode: SelectionMode = SelectionMode.Multiple
    var selectionStyle: SelectionStyle = SelectionStyle.Small
}