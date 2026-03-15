package com.example.focusnote

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import com.example.focusnote.data.model.MenuItems
import com.example.focusnote.data.model.NoteTypes

val menuItems = listOf<MenuItems>(
    MenuItems(NoteTypes.All, onClick = {}, icon = Icons.Filled.Call),
    MenuItems(NoteTypes.Archived,  onClick = {}, icon = Icons.Filled.Call),
    MenuItems(NoteTypes.Deleted,  onClick = {}, icon = Icons.Filled.Call),
)
