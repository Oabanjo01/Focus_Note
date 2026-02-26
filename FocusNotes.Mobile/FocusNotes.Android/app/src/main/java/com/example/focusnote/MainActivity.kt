package com.example.focusnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.focusnote.ui.theme.FocusNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocusNoteTheme {
                val rootModifier = Modifier.fillMaxSize()
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


    Scaffold(
        modifier
            .safeDrawingPadding()
            .padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(horizontalAlignment = Alignment.Start) {
            FilterCategory(showMenu, { showMenu = it })
            Box(modifier = Modifier.fillMaxWidth()) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    inputField = {
                        SearchBarDefaults.InputField(
                            expanded = expanded,
                            query = searchValue.text.toString(),
                            onExpandedChange = {
                                expanded = it
                            },
                            onSearch = { query ->
                                searchResults = listOfStrings.filter {
                                    it.contains(query, ignoreCase = true)
                                }
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_bar))
                            },
                            onQueryChange = {
                                searchValue.edit{replace(0, length, it)}
                            },
                            placeholder = { Text("Search") }
                        )
                    }
                ) {
                    LazyColumn {
                        items(searchResults.size) {
                                index ->
                            val searchResult = searchResults[index]

                            ListItem(
                                headlineContent = { Text(searchResult) },
                                modifier = Modifier
                                    .clickable() {
                                        expanded = false
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun FilterCategory(showMenu: Boolean = false, onShowMenuChange: (Boolean) -> Unit) {
    Box {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.homepage_title))
            IconButton(
                onClick = { onShowMenuChange(!showMenu) }) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(R.string.filter),
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        DropdownMenu(
            showMenu, onDismissRequest = { onShowMenuChange(false) }) {
            menuItems.forEach { menuItem ->
                if (menuItem.showDividerBefore) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp), thickness = Dp.Hairline
                    )
                }

                DropdownMenuItem({ Text(menuItem.text.text) }, menuItem.onClick, leadingIcon = {
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
