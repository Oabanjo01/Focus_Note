package com.example.focusnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.focusnote.ui.theme.FocusNoteTheme
import com.example.focusnote.ui.theme.Typography

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
    var searchValue = rememberTextFieldState("")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchResults by remember { mutableStateOf(listOfStrings) }

    Box(modifier = modifier.padding(top = 20.dp)) {  // ← Add this Box wrapper
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.Start
            ) {
                FilterCategory(showMenu, { showMenu = it })
            }
        }


        FloatingButton()

        SearchBar(
            expanded = expanded,
            onExpanded = { expanded = it },
            searchValue = searchValue,
            searchResults = searchResults,
            onSearchResultsChange = { searchResults = it },
            listOfStrings = listOfStrings
        )
    }
}

@Composable
fun Subtitles (text: String, modifier: Modifier = Modifier) {
    Text(text, modifier)
}

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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchBar(
    expanded: Boolean = false,
    onExpanded: (Boolean) -> Unit,
    searchValue: TextFieldState,
    searchResults: List<String>,
    onSearchResultsChange: (List<String>) -> Unit,
    listOfStrings: List<String>
) {
    val topPadding by animateDpAsState(
        if (expanded) 0.dp else 70.dp,
        label = "searchBarTopPadding"
    )
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = topPadding), // Adjust top value
        expanded = expanded,
        onExpandedChange = onExpanded,
        inputField = {
            SearchBarDefaults.InputField(
                expanded = expanded,
                query = searchValue.text.toString(),
                onExpandedChange = onExpanded,
                onSearch = { query ->
                    onSearchResultsChange(listOfStrings.filter {
                        it.contains(query, ignoreCase = true)
                    })
                    onExpanded(false)
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_bar)
                    )
                },
                onQueryChange = {
                    searchValue.edit { replace(0, length, it) }
                },
                placeholder = { Text("Search") }
            )
        }
    ) {
        LazyColumn {
            items(searchResults.size) { index ->
                ListItem(
                    headlineContent = { Text("sdf") },
                    modifier = Modifier
                        .clickable {
                            onExpanded(false)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun FilterCategory(showMenu: Boolean = false, onShowMenuChange: (Boolean) -> Unit) {
    var filteredTitle by remember { mutableStateOf(menuItems[0].text.text) }
    Box(
        modifier = Modifier.clickable(
            onClick = { onShowMenuChange(!showMenu) }
        )) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(filteredTitle, style = Typography.titleLarge.copy(fontSize = 20.sp))
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.filter),
                modifier = Modifier.size(32.dp)
            )
        }

        DropdownMenu(
            showMenu, onDismissRequest = { onShowMenuChange(false) }) {
            menuItems.forEach { menuItem ->
                if (menuItem.showDividerBefore) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp), thickness = Dp.Hairline
                    )
                }

                DropdownMenuItem({ Text(menuItem.text.text) }, {
                    filteredTitle = menuItem.text.text
                    onShowMenuChange(false)
                }, leadingIcon = {
                    Icon(
                        imageVector = menuItem.icon, contentDescription = null
                    )
                })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FocusNotePreview() {
    FocusNoteTheme {
        val rootModifier = Modifier.fillMaxSize()
        HomePage(rootModifier)
    }
}
