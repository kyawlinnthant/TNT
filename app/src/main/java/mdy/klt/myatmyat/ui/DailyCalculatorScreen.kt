package mdy.klt.myatmyat.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mdy.klt.myatmyat.R.string
import mdy.klt.myatmyat.navigation.destination.Destinations
import mdy.klt.myatmyat.theme.dimen
import mdy.klt.myatmyat.ui.components.CommonTextField
import mdy.klt.myatmyat.ui.udf.CalculatorAction
import mdy.klt.myatmyat.ui.udf.CalculatorEvent
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DailyCalculatorScreen(name: String, vm: MyViewModel, navController: NavController) {


    val winNumber = vm._winNumber.value

    val total = vm._total.value
    val percentOfTotal = vm._percentOfTotal.value
    val commissionFee = vm._commissionFee.value
    val totalLeft = vm._totalLeft.value
    val winNumberAmount = vm._winNumberAmount.value
    val totalReturnAmount = vm._totalReturnAmount.value
    val mustReturnAmount = vm._mustReturnAmount.value
    val ourTotalProfit = vm._ourTotalProfit.value
    val profitForShareOwner = vm._profitForShareOwner.value
    var profitForManager = vm._profitForManager.value
    val isMorning = vm._isMorning.value
    val saveDateInMilli = vm._dateInMilli.value
    val currentDateInMilli = vm.getCurrentDateInMilli()


    val radioOptions = listOf("Morning", "Evening")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val context = LocalContext.current
    var isWinNumberError by remember { mutableStateOf(false) }
    var isTotalInputError by remember { mutableStateOf(false) }
    var isReturnInputError by remember { mutableStateOf(false) }
    val date: String = vm._date.value
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = true) {
        vm._total.value = ""
        vm._winNumberAmount.value = ""
        vm._winNumber.value = ""
        vm._shouldShowCurrent.value = false
        vm._isMorning.value = true
        vm._date.value = vm.getCurrentDate()
    }


    val passwordVisibility by rememberSaveable {
        mutableStateOf(true)
    }


    fun calculateValidator(): Boolean {
        if (vm._winNumber.value.length < 2) {
            Timber.tag("tzo.win number").d("error")
            isWinNumberError = true
        } else {
            isWinNumberError = false
        }
        if (vm._total.value == "") {
            Timber.tag("tzo.total value").d("error")
            isTotalInputError = true
        }
        if (vm._winNumberAmount.value == "") {
            Timber.tag("tzo.win number amount").d("error")
            isReturnInputError = true
        }
        return !isWinNumberError && !isTotalInputError && !isReturnInputError
    }


    /** date picker */
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val calendar = GregorianCalendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val currentDateInMilli = Calendar.getInstance()
            val dateFormet = SimpleDateFormat("yyyy-MM-dd")
            val fullDate = dateFormet.format(calendar.time)
            val fullDateInMilli = calendar.timeInMillis
            Timber.tag("fullDate").d("$fullDateInMilli")
            Timber.tag("currentDate").d("${Calendar.getInstance().timeInMillis}")
            if (fullDateInMilli > GregorianCalendar.getInstance().timeInMillis) {
                vm.onActionCalculator(
                    CalculatorAction.ShowErrorDialog
                )
            } else {
                vm.onActionCalculator(
                    CalculatorAction.ChangeDate(
                        date = fullDate,
                        dateInMilli = fullDateInMilli
                    )
                )
                vm.getMorningTotalProfitWithDate(
                    startDate = fullDateInMilli,
                    endDate = fullDateInMilli
                )
            }
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    )


    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        vm._dateInMilli.value = vm.getCurrentDateInMilli()
        vm.calculatorEvent.collectLatest {
            when (it) {
                CalculatorEvent.NavigateToHistoryList -> {
                    navController.navigate(Destinations.HistoryList.route)
                }
                CalculatorEvent.DatePickerClick -> {
                    datePickerDialog.show()
                }
            }
        }
    }


    if (vm.historyListState.value.shouldShowErrorDialog) {
        CommonDialog(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = string.date_error_title),
            text = stringResource(id = string.date_error),
            confirmButtonLabel = stringResource(id = string.ok),
            confirmButtonType = ButtonType.TONAL_BUTTON,
            confirmButtonAction = {
                vm.onActionCalculator(
                    CalculatorAction.ErrorDialogOk
                )
            },
            confirmButtonColors = MaterialTheme.colorScheme.onPrimary,
            confirmButtonLabelColors = MaterialTheme.colorScheme.primary
        )
    }


    Scaffold(modifier = Modifier, topBar = {
        TopAppBar(
            modifier = Modifier,
            title = {
                Text(
                    text = "How Much We Can",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary),
        )
    },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                    )
                    .padding(it)
                    .padding(
                        top = MaterialTheme.dimen.base_4x,
                        start = MaterialTheme.dimen.base_2x,
                        end = MaterialTheme.dimen.base_2x
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            keyboardController?.hide()
                        }
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base)
            ) {
                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base_2x))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(MaterialTheme.dimen.base_2x)
                ) {
                    Button(
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
                        onClick = {
                            vm.onActionCalculator(
                                action = CalculatorAction.DatePickerClick
                            )
                        },
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                            Text(date, color = MaterialTheme.colorScheme.outline)
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = "Dropdown",
                                tint = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(
                            bottom = MaterialTheme.dimen.base_3x,
                            start = MaterialTheme.dimen.base_2x,
                            end = MaterialTheme.dimen.base_2x
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base_2x)
                    ) {
                        Text(
                            text = "Enter Win Number",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                        OtpTextFieldSection(
                            length = 2,
                            onFilled = {
                                Timber.tag("tzo.onfill").d("$it")
                                if (it.length == 2) {
                                    isWinNumberError = false
                                }
                                vm._winNumber.value = it
                            },
                            isError = isWinNumberError,

                            )
                    }
                    CommonTextField(
                        textFieldLabel = "Total Amount",
                        text = total,
                        onValueChange = { value ->
                            isTotalInputError = false
                            vm._total.value = value
                            vm.percentOfTotal()
                            vm.commissionFee()
                            vm.totalLeft()
                            vm.ourTotalProfit()
                            vm.profitForShareOwner()
                            vm.profitForManager()
                        },
                        passwordVisibility = passwordVisibility,
                        isError = isTotalInputError
                    )
                    CommonTextField(
                        textFieldLabel = "Return Amount",
                        text = winNumberAmount,
                        onValueChange = { value ->
                            isReturnInputError = false
                            vm._winNumberAmount.value = value
                            vm.totalReturnAmount()
                            vm.mustReturnAmount()
                            vm.ourTotalProfit()
                            vm.profitForShareOwner()
                            vm.profitForManager()
                        },
                        passwordVisibility = passwordVisibility,
                        isError = isReturnInputError
                    )
                    VerticalSpacerBase()
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base_2x)
                    ) {
                        radioOptions.forEach { text ->
                            Column(modifier = Modifier) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = (text == selectedOption),
                                        onClick = {
                                            if (text != radioOptions[0]) {
                                                vm.onActionCalculator(
                                                    action = CalculatorAction.switchClick(
                                                        isMorning = false
                                                    )
                                                )
                                                vm.getMorningTotalProfitWithDate(
                                                    startDate = saveDateInMilli,
                                                    endDate = saveDateInMilli
                                                )
                                            } else {
                                                vm.onActionCalculator(
                                                    action = CalculatorAction.switchClick(
                                                        isMorning = true
                                                    )
                                                )
                                            }
                                            onOptionSelected(text)
                                        }
                                    )
                                    Text(
                                        text = text,
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(modifier = Modifier, onClick = {
                            if (calculateValidator()) {
                                scope.launch {
                                    vm.onActionCalculator(
                                        action = CalculatorAction.SaveDataToDb(
                                            data = vm.initialValue.value.copy(
                                                winNumber = winNumber,
                                                currentTimeStamp = currentDateInMilli,
                                                saveDateMilli = saveDateInMilli,
                                                total = total.toLong(),
                                                saveDate = vm.historyDateTime(dateInMilli = saveDateInMilli),
                                                percentOfTotal = percentOfTotal.toLong(),
                                                commissionFee = commissionFee.toLong(),
                                                totalLeftAsset = totalLeft.toLong(),
                                                winNumberAmount = winNumberAmount.toLong(),
                                                totalReturnAmount = totalReturnAmount.toLong(),
                                                ourReturnAmount = mustReturnAmount.toLong(),
                                                totalProfit = ourTotalProfit.toLong(),
                                                shareOwnerProfit = profitForShareOwner.toLong(),
                                                managerProfit = if (!isMorning) {
                                                    vm.profitForManagerEvening().toLong()
                                                } else {
                                                    vm.profitForManagerMorning().toLong()
                                                },
                                                isMorning = isMorning
                                            )
                                        )
                                    )
                                    vm.getMorningTotalProfitWithDate(
                                        startDate = saveDateInMilli,
                                        endDate = saveDateInMilli
                                    )
                                    vm.onActionCalculator(
                                        action = CalculatorAction.NavigateToHistoryList
                                    )
                                }
                            }
                        }) {
                            Text(text = "Calculate")
                        }
                    }
                }
            }
        }
    )
}
