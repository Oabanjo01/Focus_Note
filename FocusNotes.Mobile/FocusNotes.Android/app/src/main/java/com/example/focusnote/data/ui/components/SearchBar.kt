package com.example.focusnote.data.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.focusnote.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun SearchBar(
  modifier: Modifier = Modifier,
  expanded: Boolean = false,
  onExpanded: (Boolean) -> Unit,
  searchValue: TextFieldState,
  searchResults: List<String>,
  onSearchResultsChange: (List<String>) -> Unit,
  listOfStrings: List<String>,
  placeholderText: String = "Search",
  filterCategoryHeight: Dp
) {

  val topPadding by animateDpAsState(
    if (expanded) 0.dp else filterCategoryHeight,
    label = "searchBarTopPadding"
  )

  SearchBar(
    modifier = modifier
      .padding(horizontal = topPadding, vertical = topPadding)
      .fillMaxWidth(),
    expanded = expanded,
    onExpandedChange = onExpanded,
    shape = RoundedCornerShape(12.dp),
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
        placeholder = { Text(placeholderText) }
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
