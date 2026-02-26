package com.example.focusnote.data

import androidx.compose.ui.graphics.vector.ImageVector

enum class NoteTypes (val text: String) {
    All("All Notes"),
    Archived("Archived"),
    Deleted("Deleted"),
}

data class MenuItems(
    val text: NoteTypes,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val showDividerBefore: Boolean = false
)
