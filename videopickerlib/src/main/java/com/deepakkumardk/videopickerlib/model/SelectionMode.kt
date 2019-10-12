package com.deepakkumardk.videopickerlib.model

sealed class SelectionMode {
    object Single : SelectionMode()
    object Multiple : SelectionMode()
}