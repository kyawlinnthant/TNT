package mdy.klt.myatmyat.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(navController: NavController, vm: MyViewModel, id: Long) {
    Scaffold {
        Surface(modifier = Modifier.padding(it)) {
            Text(text = id.toString())
            LazyColumn() {
                itemsIndexed(vm.result) {
                        index, result ->
                    if(result.id == id) {
                        Text(text = result.currentTime)
                        Text(text = result.isMorning.toString())
                        Text(text = result.netBalance.toString())
                        Text(text = result.totalBalance.toString())
                        Text(text = result.tnt.toString())
                    }
                }
            }
        }
    }
}