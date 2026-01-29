package com.example.focusnote

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


val itemsToDisplay = listOf<Triple<String, String, Color>>(
    Triple(
        "Text composable",
        "Displays text and follows the recommended Material Design guidelines.",
        Color(0xFFEADDFF)
    ),
    Triple(
        "Image composable",
        "Creates a composable that lays out and draws a given Painter class object.",
        Color(0xFFD0BCFF)
    ),
    Triple(
        "Row composable",
        "A layout composable that places its children in a horizontal sequence.",
        Color(0xFFB69DF8)
    ),
    Triple(
        "Column composable",
        "A layout composable that places its children in a vertical sequence.",
        Color(0xFFF6EDFF)
    )
)

@Composable
fun HomePage() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Tutorial(innerPadding = innerPadding)
    }
}

@Composable
fun Tutorial(innerPadding: PaddingValues = PaddingValues(all = 0.dp)) {
    val split = itemsToDisplay.chunked(2)
    Column(modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize(), verticalArrangement = Arrangement.Center) {
        for (row in split) {
            Row (modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                Log.d("Debug", "${row.count()}")
                for (item in row) {
                    Card(modifier = Modifier.weight(1f), item.first, item.second, item.third)
                }
            }
        }
    }
}

@Composable
fun Card(modifier: Modifier = Modifier, displayTitle: String = "", displayContent: String = "", color: Color = Color(0xFFEADDFF)) {
    Column (modifier = modifier
        .fillMaxHeight()
        .background(color = color)
        .padding(5.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(displayTitle, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        Text(displayContent, textAlign = TextAlign.Center)
    }
}