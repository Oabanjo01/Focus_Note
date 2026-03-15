package com.example.focusnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.focusnote.data.ui.components.FilterCategory
import com.example.focusnote.data.ui.components.SearchBar
import com.example.focusnote.ui.theme.FocusNoteTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      FocusNoteTheme {
        val rootModifier = Modifier
          .fillMaxSize()
          .safeDrawingPadding()
        HomePage(rootModifier)
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
fun HomePage(modifier: Modifier = Modifier) {
  val listOfStrings = listOf("Find", "Behavioural", "Example", "Search", "Filter", "Test")
  var showMenu by remember { mutableStateOf(false) }
  val searchValue = rememberTextFieldState("")
  var expanded by rememberSaveable { mutableStateOf(false) }
  var searchResults by remember { mutableStateOf(listOfStrings) }

  var filterCategoryHeight by remember { mutableStateOf(0.dp) }
  val density = LocalDensity.current

  Box(modifier = modifier.fillMaxSize()) {
    // Background content - only show when search is NOT expanded
    if (!expanded) {
      Scaffold(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 10.dp)
      ) { innerPadding ->
        Column(
          modifier = Modifier.padding(innerPadding),
          horizontalAlignment = Alignment.Start
        ) {
          FilterCategory
          Subtitles("Pinned")
          // NoteCards...

          FilterCategory(
            showMenu,
            modifier = Modifier.onGloballyPositioned { coordinates ->
              filterCategoryHeight = with(density) {
                coordinates.size.height.toDp()
              }
            }) { showMenu = it }

          // Your note content would go here
          // Text("Pinned")
          // NoteCards...
        }
      }
    }

    // SearchBar overlay
    SearchBar(
      expanded = expanded,
      onExpanded = { expanded = it },
      searchValue = searchValue,
      searchResults = searchResults,
      onSearchResultsChange = { searchResults = it },
      listOfStrings = listOfStrings,
      filterCategoryHeight = filterCategoryHeight
    )
  }
}

@Composable
fun Subtitles(text: String, modifier: Modifier = Modifier) {
  Text(text, modifier)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FocusNotePreview() {
  FocusNoteTheme {
    val rootModifier = Modifier.fillMaxSize()
    HomePage(rootModifier)
  }
}
