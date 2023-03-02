package mdy.klt.myatmyat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                )
                .padding(horizontal = MaterialTheme.dimen.base_2x)
        ) {
            itemsIndexed(vm.result) { index, result ->
                if (result.id == id) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .padding(vertical = MaterialTheme.dimen.base_2x)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(vertical = MaterialTheme.dimen.base_2x),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = MaterialTheme.dimen.base_2x),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(MaterialTheme.dimen.base_6x)
                                    .height(MaterialTheme.dimen.base_6x)
                                    .clip(shape = CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                if (result.totalProfit < 0) {
                                    Text(
                                        text = result.winNumber,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                } else {
                                    Text(
                                        text = result.winNumber,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = MaterialTheme.dimen.base_2x),
                                text = vm.detailDateTime(milli = result.saveDateMilli),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        VerticalSpacerBase2x()
                        RowComponent(
                            label = "Total Amount",
                            data = result.total.toString(),
                            background = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                        RowComponent(
                            label = "20% of Total",
                            data = result.percentOfTotal.toString()
                        )
                        RowComponent(
                            label = "Commission Fee (17 %)",
                            data = result.commissionFee.toString(),
                            background = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                        RowComponent(
                            label = "Our Total Asset(%နှုတ်ပြီး)",
                            data = result.totalLeftAsset.toString()
                        )
                        RowComponent(
                            label = "Win number include?(ပေါက်ကြေး)",
                            data = result.winNumberAmount.toString(),
                            background = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                        RowComponent(
                            label = "Total return amount(လျော်ကြေး)",
                            data = result.totalReturnAmount.toString()
                        )
                        RowComponent(
                            label = "Our return amount?(20%)",
                            data = result.ourReturnAmount.toString(),
                            background = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                        VerticalSpacerBase()
                        Row(
                            modifier = Modifier.padding(MaterialTheme.dimen.base_2x),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "Share Owner Profit",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.outline
                                )
                                if(result.shareOwnerProfit>0) {
                                    Text(
                                        modifier = Modifier,
                                        text = result.shareOwnerProfit.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier,
                                        text = result.shareOwnerProfit.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }

                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "Manager Profit",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.outline
                                )
                                if(result.managerProfit>0) {
                                    Text(
                                        modifier = Modifier,
                                        text = result.managerProfit.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier,
                                        text = result.managerProfit.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }

                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.dimen.base_2x),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Our total profit",
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.outline
                            )
                            if(result.totalProfit>0) {
                                Text(
                                    modifier = Modifier,
                                    text = result.totalProfit.toString(),
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.End
                                )
                            } else {
                                Text(
                                    modifier = Modifier,
                                    text = result.totalProfit.toString(),
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.End
                                )
                            }

                        }
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

@Composable
fun RowComponent(
    label: String = "",
    data: String = "",
    background: Color = MaterialTheme.colorScheme.surface,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .padding(
                vertical = MaterialTheme.dimen.base_2x,
                horizontal = MaterialTheme.dimen.base_2x
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(4f),
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            modifier = Modifier.weight(1f),
            text = data,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.End
        )
    }
}