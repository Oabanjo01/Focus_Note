package com.example.focusnote.data.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun FloatingButton () {
  FloatingActionButton (
    onClick = {  },
    containerColor = MaterialTheme.colorScheme.secondary,
    contentColor = MaterialTheme.colorScheme.primary,
  ) {
    Icon(Icons.Filled.Add, "Floating action button.")
  }
}
