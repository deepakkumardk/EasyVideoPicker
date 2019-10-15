package com.deepakkumardk.videopickerlib.model

/**
 * @author Deepak Kumar
 * @since 13/10/19
 */
sealed class SelectionStyle {
    object Small : SelectionStyle()
    object Large : SelectionStyle()
}