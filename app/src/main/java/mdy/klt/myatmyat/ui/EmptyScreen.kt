package mdy.klt.myatmyat.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.theme.dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyScreen(text: String) {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = text, modifier = Modifier
            .align(Alignment.TopStart)
            .padding(horizontal = MaterialTheme.dimen.base_2x, vertical = MaterialTheme.dimen.base), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
        Image(painter = painterResource(id = R.drawable.empty), contentDescription = "Empty View", modifier = Modifier
            .align(Alignment.Center))
    }
}
