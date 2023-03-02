package mdy.klt.myatmyat.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.navigation.destination.Destinations
import mdy.klt.myatmyat.theme.dimen
import mdy.klt.myatmyat.ui.components.DateDialog
import mdy.klt.myatmyat.ui.components.FilterDateSheetView
import mdy.klt.myatmyat.ui.domain.FilterType
import mdy.klt.myatmyat.ui.udf.HistoryListAction
import mdy.klt.myatmyat.ui.udf.HistoryListEvent
import timber.log.Timber
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HistoryListScreen(navController: NavController, vm: MyViewModel) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    /** date picker */
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

            val calendar = GregorianCalendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val fullDateInMilli = calendar.timeInMillis
            vm._singleDateMilli.value = fullDateInMilli
            vm.onActionHistoryList(
                action = HistoryListAction.FilterDate
            )
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    )

    if (vm.historyListState.value.shouldShowStartDateErrorDialog) {
        CommonDialog(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.date_error_title),
            text = stringResource(id = R.string.start_date_error),
            confirmButtonLabel = stringResource(id = R.string.ok),
            confirmButtonType = ButtonType.TONAL_BUTTON,
            confirmButtonAction = {
                vm.onActionHistoryList(
                    HistoryListAction.StartDateErrorDialogOk
                )
            },
            confirmButtonColors = MaterialTheme.colorScheme.onPrimary,
            confirmButtonLabelColors = MaterialTheme.colorScheme.primary
        )
    }

    if (vm.historyListState.value.shouldShowEndDateErrorDialog) {
        CommonDialog(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.date_error_title),
            text = vm.historyListState.value.endDateErrorText,
            confirmButtonLabel = stringResource(id = R.string.ok),
            confirmButtonType = ButtonType.TONAL_BUTTON,
            confirmButtonAction = {
                vm.onActionHistoryList(
                    HistoryListAction.EndDateErrorDialogOk
                )
            },
            confirmButtonColors = MaterialTheme.colorScheme.onPrimary,
            confirmButtonLabelColors = MaterialTheme.colorScheme.primary
        )
    }


    LaunchedEffect(key1 = true) {
        vm.historyListEvent.collectLatest {
            when (it) {
                HistoryListEvent.NavigateToCalculator -> {
                    navController.navigate(Destinations.DailyCalculator.route)
                }
                is HistoryListEvent.NavigateToHistoryDetail -> {
                    navController.navigate(Destinations.HistoryDetail.passId(dataId = it.id))
                }
                HistoryListEvent.ShowDateTimeDialog -> {
                    modalBottomSheetState.show()
                }
            }
        }
    }


    if (vm.historyListState.value.shouldShowDialog) {
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
            confirmButtonType = ButtonType.SOLID_BUTTON,
            confirmButtonAction = {
                scope.launch {
                    if (vm.historyListState.value.deleteItem == vm.result.first().id) {
                        vm._shouldShowCurrent.value = false
                    }
                    vm.onActionHistoryList(
                        action = HistoryListAction.DeleteHistoryItem(vm.historyListState.value.deleteItem)
                    )
                    vm.getHistory()
                }
            },
            confirmButtonColors = MaterialTheme.colorScheme.error,
            confirmButtonLabelColors = MaterialTheme.colorScheme.onError
        )
    }

    if (vm.historyListState.value.shouldShowDeleteAllDialog) {
        CommonDialog(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.delete_confirm_title),
            text = stringResource(id = R.string.delete_all_confirm),
            dismissButtonLabel = stringResource(id = R.string.cancel_button),
            dismissAction = {
                vm.onActionHistoryList(
                    action = HistoryListAction.DismissDeleteAllConfirmDialog
                )
            },
            confirmButtonLabel = stringResource(id = R.string.confirm_button),
            confirmButtonType = ButtonType.SOLID_BUTTON,
            confirmButtonAction = {
                scope.launch {
                    vm.onActionHistoryList(
                        action = HistoryListAction.DeleteAllHistoryItem
                    )
                }
            },
            confirmButtonColors = MaterialTheme.colorScheme.error,
            confirmButtonLabelColors = MaterialTheme.colorScheme.onError
        )
    }

    if (vm.historyListState.value.shouldShowDatePickerDialog) {
        DateDialog(vm = vm, disMissDialog = {
            vm.onActionHistoryList(
                action = HistoryListAction.HideDatePickerDialog
            )
        })
    }
    ModalBottomSheetLayout(
        sheetContent = {
            FilterDateSheetView(
                title = stringResource(id = R.string.date_error_title),
                onItemClick = {
                    when (it) {
                        FilterType.ALL_TIME.select -> {
                            Timber.tag("All Time").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.ALL_TIME.select
                                vm._titleName.value = "All Time Result"
                                vm.getHistory()
                                modalBottomSheetState.hide()
                            }
                        }
                        FilterType.TODAY.select -> {
                            Timber.tag("Today").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.TODAY.select
                                vm._titleName.value = "Today Result"
                                vm.getHistoryToday()
                                modalBottomSheetState.hide()
                            }
                        }
                        FilterType.YESTERDAY.select -> {
                            Timber.tag("Yesterday").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.YESTERDAY.select
                                vm._titleName.value = "Yesterday Result"
                                vm.getHistoryYesterday()
                                modalBottomSheetState.hide()
                            }
                        }
                        FilterType.THIS_WEEK.select -> {
                            Timber.tag("This Week").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.THIS_WEEK.select
                                vm._titleName.value = "This Week Result"
                                vm.getHistoryThisWeek()
                                modalBottomSheetState.hide()
                            }
                        }
                        FilterType.LAST_WEEK.select -> {
                            Timber.tag("Last Week").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.LAST_WEEK.select
                                vm._titleName.value = "Last Week Result"
                                vm.getHistoryLastWeek()
                                modalBottomSheetState.hide()
                            }
                        }
                        FilterType.THIS_MONTH.select -> {
                            Timber.tag("This Month").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.THIS_MONTH.select
                                vm._titleName.value = "This Month Result"
                                vm.getHistoryThisMonth()
                                modalBottomSheetState.hide()
                            }
                        }
                        FilterType.LAST_MONTH.select -> {
                            Timber.tag("Last Month").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.LAST_MONTH.select
                                vm._titleName.value = "Last Month Result"
                                vm.getHistoryLastMonth()
                                modalBottomSheetState.hide()
                            }
                        }
                        FilterType.SINGLE_DAY.select -> {
                            Timber.tag("Single Day").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.SINGLE_DAY.select
                                vm._titleName.value = "Single Day Result"
                                datePickerDialog.show()
                                modalBottomSheetState.hide()
                            }
                        }
                        FilterType.DATE_RANGE.select -> {
                            Timber.tag("" + "Date Range").d("Click")
                            scope.launch {
                                vm._shouldShowCurrent.value = false
                                vm._filteredState.value = FilterType.DATE_RANGE.select
                                vm._titleName.value = "Date Range Result"
                                vm.dateRangeReset()
                                vm.onActionHistoryList(
                                    action = HistoryListAction.ShowDatePickerDialog
                                )
                                modalBottomSheetState.hide()
                            }
                        }
                    }
                }
            )
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = MaterialTheme.dimen.base_2x,
            topEnd = MaterialTheme.dimen.base_2x,
        ),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        scrimColor = Color.Black.copy(0.7f),
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Profit Data List",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        actions = {
                            IconButton(onClick = {
                                vm.onActionHistoryList(
                                    action = HistoryListAction.DeleteAllDialog
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete All Data",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
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
                            IconButton(onClick = {
                                vm.onActionHistoryList(
                                    action = HistoryListAction.DateFilterDialog
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = "Date Filter",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
                    )
                },
                content = {
                    var profit: Long = 0L
                    var profitForManager = 0L
                    var profitForAgent = 0L

                    vm.result.forEach {
                        profit += it.totalProfit
                        profitForManager += it.managerProfit
                        profitForAgent += it.commissionFee
                    }
                    val netProfit = profit - profitForManager
                    val profitForShareOwner = netProfit / 5
                    if(vm.result.isEmpty()) {
                        Column(modifier = Modifier.padding(it)) {
                            EmptyScreen(text = vm._titleName.value)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                )
                                .padding(
                                    horizontal = MaterialTheme.dimen.base_2x
                                )
                        ) {
                            itemsIndexed(vm.result) { index, result ->
                                if (index == 0) {
                                    Row(modifier = Modifier.padding(vertical = MaterialTheme.dimen.base)) {
                                        Text(
                                            text = vm._titleName.value,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = MaterialTheme.dimen.base),
                                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base_2x),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f, fill = false),
                                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(
                                                    MaterialTheme.dimen.base
                                                )
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .clip(
                                                            shape = RoundedCornerShape(
                                                                MaterialTheme.dimen.base
                                                            )
                                                        )
                                                        .background(MaterialTheme.colorScheme.surface)
                                                        .padding(MaterialTheme.dimen.base_2x)
                                                ) {
                                                    Text(
                                                        text = "Total Profit",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.outline
                                                    )
                                                    if (profit > 0) {
                                                        Text(
                                                            text = profit.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    } else {
                                                        Text(
                                                            text = profit.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.error,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                                Column(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .clip(
                                                            shape = RoundedCornerShape(
                                                                MaterialTheme.dimen.base
                                                            )
                                                        )
                                                        .background(MaterialTheme.colorScheme.surface)
                                                        .padding(MaterialTheme.dimen.base_2x)
                                                ) {
                                                    Text(
                                                        text = "Manager Profit",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.outline
                                                    )
                                                    if (profitForManager > 0) {
                                                        Text(
                                                            text = profitForManager.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    } else {
                                                        Text(
                                                            text = "0",
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.error,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                            }

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(
                                                    MaterialTheme.dimen.base
                                                )
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .clip(
                                                            shape = RoundedCornerShape(
                                                                MaterialTheme.dimen.base
                                                            )
                                                        )
                                                        .background(MaterialTheme.colorScheme.surface)
                                                        .padding(MaterialTheme.dimen.base_2x)
                                                ) {
                                                    Text(
                                                        text = "Net Profit",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.outline
                                                    )
                                                    if (netProfit > 0) {
                                                        Text(
                                                            text = netProfit.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    } else {
                                                        Text(
                                                            text = netProfit.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.error,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                                Column(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .clip(
                                                            shape = RoundedCornerShape(
                                                                MaterialTheme.dimen.base
                                                            )
                                                        )
                                                        .background(MaterialTheme.colorScheme.surface)
                                                        .padding(MaterialTheme.dimen.base_2x)
                                                ) {
                                                    Text(
                                                        text = "Deposit Left",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.outline
                                                    )
                                                    if (profit > 0) {
                                                        Text(
                                                            text = vm._totalDeposit.value.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    } else {
                                                        val deposit = vm._totalDeposit.value + profit
                                                        Text(
                                                            text = deposit.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.error,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                            }
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(
                                                    MaterialTheme.dimen.base
                                                )
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .clip(
                                                            shape = RoundedCornerShape(
                                                                MaterialTheme.dimen.base
                                                            )
                                                        )
                                                        .background(MaterialTheme.colorScheme.surface)
                                                        .padding(MaterialTheme.dimen.base_2x)
                                                ) {
                                                    Text(
                                                        text = "Share Owner Profit",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.outline
                                                    )
                                                    if (profitForShareOwner > 0) {
                                                        Text(
                                                            text = profitForShareOwner.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    } else {
                                                        Text(
                                                            text = profitForShareOwner.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.error,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                                Column(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .clip(
                                                            shape = RoundedCornerShape(
                                                                MaterialTheme.dimen.base
                                                            )
                                                        )
                                                        .background(MaterialTheme.colorScheme.surface)
                                                        .padding(MaterialTheme.dimen.base_2x)
                                                ) {
                                                    Text(
                                                        text = "Agent Fees",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.outline
                                                    )
                                                    if (profitForAgent > 0) {
                                                        Text(
                                                            text = profitForAgent.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    } else {
                                                        Text(
                                                            text = profitForAgent.toString(),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.error,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = MaterialTheme.dimen.base)
                                        .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base_2x))
                                        .clickable(onClick = {
                                            vm.onActionHistoryList(
                                                action = HistoryListAction.ShowHistoryDetail(id = result.id!!)
                                            )
                                        })
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
                                            horizontalArrangement = Arrangement.spacedBy(
                                                MaterialTheme.dimen.base
                                            ),
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
                                                    text = result.winNumber,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = result.saveDate,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                if (result.isMorning) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(
                                                            MaterialTheme.dimen.tiny
                                                        )
                                                    ) {
                                                        Text(
                                                            modifier = Modifier.align(Alignment.CenterVertically),
                                                            text = "MORNING",
                                                            style = MaterialTheme.typography.labelSmall,
                                                            color = MaterialTheme.colorScheme.outline
                                                        )
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.ic_sun),
                                                            contentDescription = "day",
                                                            tint = MaterialTheme.colorScheme.outline,
                                                            modifier = Modifier
                                                                .size(12.dp)
                                                                .align(Alignment.CenterVertically)
                                                        )
                                                    }
                                                } else {
                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(
                                                            MaterialTheme.dimen.tiny
                                                        )
                                                    ) {
                                                        Text(
                                                            modifier = Modifier.align(Alignment.CenterVertically),
                                                            text = "EVENING",
                                                            style = MaterialTheme.typography.labelSmall,
                                                            color = MaterialTheme.colorScheme.outline
                                                        )
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.ic_moon),
                                                            contentDescription = "night",
                                                            tint = MaterialTheme.colorScheme.outline,
                                                            modifier = Modifier
                                                                .size(12.dp)
                                                                .align(Alignment.CenterVertically)
                                                        )
                                                    }
                                                }
                                            }
                                            if (index == 0 && vm._shouldShowCurrent.value) {
                                                InfiniteAnimation()
                                            }
                                            IconButton(
                                                modifier = Modifier,
                                                onClick = {
                                                    vm.onActionHistoryList(
                                                        action = HistoryListAction.ShowDeleteConfirmDialog(
                                                            id = result.id!!
                                                        )
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
                                            horizontalArrangement = Arrangement.spacedBy(
                                                MaterialTheme.dimen.base
                                            )
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                                                    .background(
                                                        MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.05f
                                                        )
                                                    )
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
                                                    text = result.total.toString(),
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
                                                    .background(
                                                        MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.05f
                                                        )
                                                    )
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
                                                if (result.totalProfit > 0) {
                                                    Text(
                                                        text = result.totalProfit.toString(),
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                } else {
                                                    Text(
                                                        text = result.totalProfit.toString(),
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = MaterialTheme.colorScheme.error
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
            )
        }
    )
}

@Composable
fun VerticalSpacerBase() {
    Spacer(modifier = Modifier.height(MaterialTheme.dimen.base))
}

@Composable
fun VerticalSpacerBase2x() {
    Spacer(modifier = Modifier.height(MaterialTheme.dimen.base_2x))
}

@Composable
fun InfiniteAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val heartSize by infiniteTransition.animateFloat(
        initialValue = 12.0f,
        targetValue = 14.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, delayMillis = 100, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Text(
        text = "Current",
        color = MaterialTheme.colorScheme.primary,
        fontSize = heartSize.sp,
        fontWeight = FontWeight.Bold
    )

}


