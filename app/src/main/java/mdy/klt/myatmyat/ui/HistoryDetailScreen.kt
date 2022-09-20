package mdy.klt.myatmyat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mdy.klt.myatmyat.theme.dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(navController: NavController, vm: MyViewModel, id: Long) {
    Scaffold(modifier = Modifier, topBar = {
        TopAppBar(
            modifier = Modifier,
            title = {
                Text(
                    text = "History Detail",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary),

            )
    }) {
        LazyColumn(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                )
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimen.base)
        ) {
            itemsIndexed(vm.result) { index, result ->
                if (result.id == id) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(it)
                            .padding(top = MaterialTheme.dimen.base_2x)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(all = MaterialTheme.dimen.base)
                    ) {
                        CommonText(text = "Date")
                        CommonText(
                            text = vm.detailDateTime(milli = result.saveDateMilli)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(all = MaterialTheme.dimen.base)
                    ) {
                        CommonText(text = "Win Number")
                        if(result.totalProfit<0) {
                            CommonText(
                                text = result.winNumber.toString(),
                                colors = MaterialTheme.colorScheme.error
                            )
                        } else {
                            CommonText(
                                text = result.winNumber.toString(),
                                colors = Color.Green.copy(0.6f)
                            )
                        }
                    }
                    Row(
                        modifier =
                        Modifier.clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base)
                    ) {
                            CommonText(text = "Total Amount")
                            CommonText(
                                text = result.total.toString()
                            )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                                verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "20% of Total")
                        CommonText(
                            text = result.percentOfTotal.toString(),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "Commission Fee (17 %)")
                        CommonText(
                            text = result.commissionFee.toString(),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "Our Total Asset(%နှုတ်ပြီးစုစုပေါင်းကျန်ငွေ)")
                        CommonText(
                            text = result.totalLeftAsset.toString(),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "Win number include?(ပေါက်ကြေးစုစုပေါင်း)")
                        CommonText(
                            text = result.winNumberAmount.toString(),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "Total return amount(လျော်ကြေးစုစုပေါင်း)")
                        CommonText(
                            text = result.totalReturnAmount.toString(),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "Our return amount?(20%)")
                        CommonText(
                            text = result.ourReturnAmount.toString()
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "Our total profit")
                        if(result.totalProfit<0) {
                            CommonText(
                                text = result.totalProfit.toString(),
                                colors = MaterialTheme.colorScheme.error
                            )
                        } else {
                            CommonText(
                                text = result.totalProfit.toString(),
                                colors = Color.Green.copy(0.6f)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "Share Owner profit")
                        CommonText(
                            text = result.shareOwnerProfit.toString()
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(all = MaterialTheme.dimen.base),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonText(text = "Manager Profit")
                        CommonText(
                            text = result.managerProfit.toString(),
                        )
                    }
//
//            LazyColumn(
//                modifier = Modifier
//                    .padding(top = 10.dp)
//                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                items(history) { result ->
//                    Text(text = result.toString())
//                }
//            }

                }
            }
        }
    }
}