package mdy.klt.myatmyat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.navigation.destination.Destinations
import mdy.klt.myatmyat.theme.dimen
import mdy.klt.myatmyat.ui.udf.CalculatorEvent
import mdy.klt.myatmyat.ui.udf.HistoryListAction
import mdy.klt.myatmyat.ui.udf.HistoryListEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryListScreen(navController: NavController, vm: MyViewModel) {

    val shouldShowDialog = vm.historyListState.value.shouldShowDialog

    LaunchedEffect(key1 = true) {
        vm.historyListEvent.collectLatest {
            when (it) {
                HistoryListEvent.NavigateToCalculator -> {
                    navController.navigate(Destinations.DailyCalculator.route)
                }
            }
        }
    }

    if(vm.historyListState.value.shouldShowDialog) {
        CommonDialog(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.delete_confirm_title),
            text = stringResource(id = R.string.delete_confirm),
            dismissButtonLabel = stringResource(id = R.string.cancel_button),
            dismissAction = {
                vm.onActionHistoryList(
                    action = HistoryListAction.DismissDeleteConfirmDialog
                )
            },
            confirmButtonLabel = stringResource(id = R.string.confirm_button),
            confirmButtonType = ButtonType.TONAL_BUTTON,
            confirmButtonAction = {
                vm.onActionHistoryList(
                    action = HistoryListAction.DeleteHistoryItem(vm.historyListState.value.deleteItem)
                )
            }
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text( text = "2D Data List",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = {
                        vm.onActionHistoryList(
                            action = HistoryListAction.NavigateToCalculator
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add New Data",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(it)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                    )
                    .padding(
                        horizontal = MaterialTheme.dimen.base_2x
                    )
            ) {
                itemsIndexed(vm.result) { index, result ->
                    if (index == 0) {
                        VerticalSpacerBase()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.dimen.base)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base_2x))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(
                                horizontal = MaterialTheme.dimen.base_2x,
                                vertical = MaterialTheme.dimen.base
                            ),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base_2x),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f, fill = false),
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(MaterialTheme.dimen.base_4x)
                                        .height(MaterialTheme.dimen.base_4x)
                                        .clip(shape = CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "24",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = result.currentTime,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "MORNING",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                                IconButton(
                                    modifier = Modifier,
                                    onClick = {
                                        vm.onActionHistoryList(
                                            action = HistoryListAction.showDeleteConfirmDialog(id = result.id!!)
                                        )
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outline_delete_24),
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(MaterialTheme.dimen.small_icon)
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                                        .padding(
                                            vertical = MaterialTheme.dimen.small,
                                            horizontal = MaterialTheme.dimen.base
                                        )
                                ) {
                                    Text(
                                        text = "Total Value",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                    Text(
                                        text = result.totalBalance.toString(),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                                        .padding(
                                            vertical = MaterialTheme.dimen.small,
                                            horizontal = MaterialTheme.dimen.base
                                        )
                                ) {
                                    Text(
                                        text = "Profit",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                    Text(
                                        text = result.totalBalance.toString(),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun VerticalSpacerBase() {
    Spacer(modifier = Modifier.height(MaterialTheme.dimen.base))
}
