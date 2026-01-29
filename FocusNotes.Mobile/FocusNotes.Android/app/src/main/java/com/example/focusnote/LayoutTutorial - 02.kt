package com.example.focusnote

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusnote.ui.theme.FocusNoteTheme

@Composable
fun HomePage02() {
    Scaffold { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            BusinessCard(Modifier)
            BusinessDetail(Modifier)
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    val image = R.drawable.android_logo
    Column(
        modifier.background(color = Color(0xFFD1FFD7)),
        verticalArrangement = Arrangement.spacedBy(6.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier
                .background(color = Color(0xFF132A13))
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource((image)),
                contentDescription = null,
                modifier
                    .width(100.dp)
                    .align(alignment = Alignment.Center)
            )
        }

        Text(stringResource(R.string.business_name), fontSize = 28.sp)
        Text(stringResource(R.string.business_title))
    }
}

@Composable
fun BusinessDetail(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(bottom = 25.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column (horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(7.5.dp,)) {
        userContact.forEach {
            ContactDetail(it)

        }

        }
    }
}


val userContact = listOf<Pair<ImageVector, String>>(
    Pair(Icons.Default.Call, "+234 811 376 2123"),
    Pair(Icons.Default.Share, "@AndroidDev"),
    Pair(Icons.Default.Email, "banjolakunri@gnail.com"),
)

@Composable
fun ContactDetail(item: Pair<ImageVector, String>) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        Icon(
            imageVector = item.first, contentDescription = null, tint = Color(0xFF132A13)
        )
        Text(item.second)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    FocusNoteTheme {
        HomePage02()
    }
}