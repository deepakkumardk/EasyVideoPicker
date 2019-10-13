package com.deepakkumardk.videopickerlib.model

sealed class SelectionMode {
    object Single : SelectionMode()
    object Multiple : SelectionMode()
    data class Custom(val limit:Int) : SelectionMode()
}