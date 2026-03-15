package com.example.focusnote.data.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusnote.R
import com.example.focusnote.menuItems
import com.example.focusnote.ui.theme.Typography

@Composable
fun FilterCategory(showMenu: Boolean = false,  modifier: Modifier = Modifier, onShowMenuChange: (Boolean) -> Unit,) {
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
