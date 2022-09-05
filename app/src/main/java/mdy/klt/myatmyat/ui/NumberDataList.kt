package mdy.klt.myatmyat.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberDataList() {
    val vm: MyViewModel = hiltViewModel()

    Scaffold(modifier = Modifier, topBar = {
        SmallTopAppBar(
            modifier = Modifier,
            title = { Text(text = "Calculator") },
            colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
        )
    }) {
        LazyRow(
            modifier = Modifier
                .padding(it)
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(vm.result) { result ->
                Text(text = result.toString())
            }
        }
    }
}