package mdy.klt.myatmyat.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.navigation.destination.Destinations
import mdy.klt.myatmyat.theme.dimen
import mdy.klt.myatmyat.ui.domain.DaysOfWeek
import mdy.klt.myatmyat.ui.udf.HistoryListAction
import mdy.klt.myatmyat.ui.udf.HistoryListEvent
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryListScreen(navController: NavController, vm: MyViewModel) {

    val shouldShowDialog = vm.historyListState.value.shouldShowDialog
    var visible by remember { mutableStateOf(true) }
    val context = LocalContext.current
    /** date picker */
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

//            vm.onActionEdit(
//                ProfileEditAction.ChangeDob(
//                    dob = "$year-${month + 1}-$dayOfMonth"
//                )
//            )
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    )


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
                    datePickerDialog.show()
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getCalculatedMonths( month: Int): String? {
        val c: Calendar = GregorianCalendar()
        c.add(Calendar.MONTH, -month)
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH))
        Timber.tag("get month").d("${c.timeInMillis}")
        val fullDate = SimpleDateFormat("yyyy-MM-dd-E")
        val day = SimpleDateFormat("E")
        Timber.tag("get month").d("${fullDate.format(c.time)}")
        Timber.tag("get month").d("${day.format(c.time)}")
        return fullDate.format(c.time).toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun getCalculatedDays( day: Int): String? {
        val c: Calendar = GregorianCalendar()
        c.add(Calendar.DAY_OF_MONTH, -day)
        c.set(Calendar.DAY_OF_WEEK, 2)
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        val fullDay = SimpleDateFormat("yyyy-MM-dd-E")
        val dayFormat = SimpleDateFormat("E")
        val dayName = fullDay.format(c.time)
        when(dayOfWeek) {
            Calendar.MONDAY -> {
                Timber.tag("Today is Monday").d("Yes")
            }
            Calendar.TUESDAY -> {
                Timber.tag("Today is Tuesday").d("Yes")
            }
            Calendar.WEDNESDAY -> {
                Timber.tag("Today is Wednesday").d("Yes")
            }
            Calendar.THURSDAY -> {
                Timber.tag("Today is Thursday").d("Yes")
            }
            Calendar.FRIDAY -> {
                Timber.tag("Today is Friday").d("Yes")
            }
            Calendar.SATURDAY -> {
                Timber.tag("Today is Saturday").d("Yes")
            }
            Calendar.SUNDAY -> {
                Timber.tag("Today is Sunday").d("Yes")
            }
        }
        Timber.tag("get day").d("$dayName")
        Timber.tag("get day").d("$dayOfWeek")

        return fullDay.format(c.time).toString()
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
            confirmButtonType = ButtonType.TONAL_BUTTON,
            confirmButtonAction = {
                if (vm.historyListState.value.deleteItem == vm.result.first().id) {
                    vm._shouldShowCurrent.value = false
                }
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
                    Text(
                        text = "2D Data List",
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
                    IconButton(onClick = {
                        vm.onActionHistoryList(
                            action = HistoryListAction.DatePickerDialog
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Select Time",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
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
                            .clickable(onClick = {
                                Timber
                                    .tag("tzo.detail.trigger")
                                    .d("ok")
                                getCalculatedDays(7)
                                getCalculatedMonths(2)
                                vm.onActionHistoryList(
                                    action = HistoryListAction.ShowHistoryDetail(id = result.id!!)
                                )
                                //   HistoryListAction.ShowHistoryDetail(id = result.id!!)
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
                                        text = result.winNumber.toString(),
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
                                        Text(
                                            text = "MORNING",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    } else {
                                        Text(
                                            text = "EVENING",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                }
                                if (index == 0 && vm._shouldShowCurrent.value) {
                                    InfiniteAnimation()
                                }
                                IconButton(
                                    modifier = Modifier,
                                    onClick = {
                                        vm.onActionHistoryList(
                                            action = HistoryListAction.ShowDeleteConfirmDialog(id = result.id!!)
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
                                        text = result.total.toString(),
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
        },
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
        targetValue = 15.0f,
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
