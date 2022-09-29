package mdy.klt.myatmyat.ui

import android.annotation.SuppressLint
import android.text.format.DateFormat
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import mdy.klt.myatmyat.data.PayOff
import mdy.klt.myatmyat.repository.HistoryRepository
import mdy.klt.myatmyat.ui.udf.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.roundToLong

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repo: HistoryRepository
) : ViewModel() {
    private val _result = mutableStateListOf<PayOff>()
    val result: MutableList<PayOff> get() = _result

    private val _resultForManager = mutableStateListOf<PayOff>()
    val resultForManager: MutableList<PayOff> get() = _resultForManager

    private val _initialValue = mutableStateOf(PayOff())
    val initialValue: MutableState<PayOff> get() = _initialValue

    val _winNumber = mutableStateOf("")
    private val winNumber: MutableState<String> get() = _winNumber

    val _percentOfTotal = mutableStateOf("0")
    private val percentOfTotal: MutableState<String> get() = _percentOfTotal

    val _commissionFee = mutableStateOf("0")
    private val commissionFee: MutableState<String> get() = _commissionFee

    val _totalLeft = mutableStateOf("0")
    private val totalLeft: MutableState<String> get() = _totalLeft

    val _winNumberAmount = mutableStateOf("")
    private val winNumberAmount: MutableState<String> get() = _winNumberAmount

    val _totalReturnAmount = mutableStateOf("0")
    private val totalReturnAmount: MutableState<String> get() = _totalReturnAmount

    val _mustReturnAmount = mutableStateOf("0")
    private val mustReturnAmount: MutableState<String> get() = _mustReturnAmount

    val _ourTotalProfit = mutableStateOf("0")
    private val ourTotalProfit: MutableState<String> get() = _ourTotalProfit

    val _profitForShareOwner = mutableStateOf("0")
    private val profitForShareOwner: MutableState<String> get() = _profitForShareOwner

    val _profitForManager = mutableStateOf("0")
    private val profitForManager: MutableState<String> get() = _profitForManager

    val _isMorning = mutableStateOf(true)
    private val isMorning: MutableState<Boolean> get() = _isMorning

    private val _calculatorEvent = MutableSharedFlow<CalculatorEvent>()
    val calculatorEvent: SharedFlow<CalculatorEvent> get() = _calculatorEvent

    private val _historyListEvent = MutableSharedFlow<HistoryListEvent>()
    val historyListEvent: SharedFlow<HistoryListEvent> get() = _historyListEvent

    private val _historyListState = mutableStateOf(HistoryListState())
    val historyListState: State<HistoryListState> get() = _historyListState

    val _shouldShowCurrent = mutableStateOf(false)
    private val shouldShowCurrent: MutableState<Boolean> get() = _shouldShowCurrent

    val _date = mutableStateOf(getCurrentDate())
    private val date: MutableState<String> get() = _date

    val _startDate = mutableStateOf(getCurrentDate())
    private val startDate: MutableState<String> get() = _startDate

    val _startDateMilli = mutableStateOf(getCurrentDateInMilli())
    private val startDateMilli: MutableState<Long> get() = _startDateMilli

    val _endDate = mutableStateOf(getCurrentDate())
    private val endDate: MutableState<String> get() = _endDate

    val _endDateMilli = mutableStateOf(getCurrentDateInMilli())
    private val endDateMilli: MutableState<Long> get() = _endDateMilli

    val _singleDateMilli = mutableStateOf(getCurrentDateInMilli())
    private val singleDateMilli: MutableState<Long> get() = _singleDateMilli

    val _dateInMilli = mutableStateOf(getCurrentDateInMilli())
    private val dateInMilli: MutableState<Long> get() = _dateInMilli

    val _shouldShowErrorDialog = mutableStateOf(false)
    private val shouldShowErrorDialog: MutableState<Boolean> get() = _shouldShowErrorDialog

    val _titleName = mutableStateOf("All Time Result")
    private val titleName: MutableState<String> get() = _titleName

    val _filterTotal = mutableStateOf(0L)
    private val filterTotal: MutableState<Long> get() = _filterTotal

    var _realResult = mutableStateListOf<Long>()
    private val realResult: MutableList<Long> get() = _realResult

    val _total = mutableStateOf("")
    val total: MutableState<String> get() = _total

    val _filteredState = mutableStateOf(0)
    private val filteredState: MutableState<Int> get() = _filteredState

    val _totalDeposit = mutableStateOf(2500000L)
    private val totalDeposit: MutableState<Long> get() = _totalDeposit


    init {
        viewModelScope.launch {
            getHistory()
        }
//        }
//        when(filteredState.value) {
//            FilterType.All_TIME.select ->  {
//                getHistory()
//            }
//            FilterType.TODAY.select -> {
//                getHistoryWithDate(startDate = todayStartMillis(), endDate = todayEndMillis())
//            }
//            FilterType.YESTERDAY.select -> {
//                getHistoryWithDate(startDate = yesterdayStartMillis(), endDate = yesterdayEndMillis())
//            }
//            FilterType.THIS_WEEK.select -> {
//                getHistoryWithDate(startDate = weekStartMillis(), endDate = weekEndMillis())
//            }
//            FilterType.LAST_WEEK.select -> {
//                getHistoryWithDate(startDate = previousWeekStartMillis(), endDate = previousWeekEndMillis())
//            }
//            FilterType.THIS_MONTH.select -> {
//                getHistoryWithDate(startDate = currentMonthStartMillis(), endDate = currentMonthEndMillis())
//            }
//            FilterType.LAST_MONTH.select -> {
//                getHistoryWithDate(startDate = previousMonthStartMillis(), endDate = previousMonthEndMillis())
//            }
//            FilterType.SINGLE_DAY.select -> {
//                getHistoryWithDate(startDate = weekStartMillis(), endDate = weekEndMillis())
//            }
//            FilterType.DATE_RANGE.select -> {
//
//            }
//        }

        Timber.tag("get History").d("trigger")
    }

    fun dateRangeReset() {
        _startDate.value = getCurrentDate()
        _startDateMilli.value = getCurrentDateInMilli()
        _endDate.value = getCurrentDate()
        _endDateMilli.value = getCurrentDateInMilli()
    }

fun getHistoryToday() {
    getHistoryWithDate(startDate = todayStartMillis(), endDate = todayEndMillis())
}

fun getHistoryYesterday() {
    getHistoryWithDate(startDate = yesterdayStartMillis(), endDate = yesterdayEndMillis())
}

fun getHistoryThisWeek() {
    getHistoryWithDate(startDate = weekStartMillis(), endDate = weekEndMillis())
}

fun getHistoryLastWeek() {
    getHistoryWithDate(startDate = previousWeekStartMillis(), endDate = previousWeekEndMillis())
}

fun getHistoryThisMonth() {
    getHistoryWithDate(startDate = currentMonthStartMillis(), endDate = currentMonthEndMillis())
}

fun getHistoryLastMonth() {
    getHistoryWithDate(startDate = previousMonthStartMillis(), endDate = previousMonthEndMillis())
}

fun getHistorySingleDay() {
    getHistoryWithDate(startDate = calendarDateStart(date = singleDateMilli.value), endDate = calendarDateEnd(date = singleDateMilli.value))
}

    fun getHistoryDateRange() {
        getHistoryWithDate(startDate = calendarDateStart(date = startDateMilli.value), endDate = calendarDateEnd(date = endDateMilli.value))
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        val calendar = GregorianCalendar.getInstance()
        val fullDate = SimpleDateFormat("dd-MM-yyyy")
        Timber.tag("get month").d(fullDate.format(calendar.time))
        return fullDate.format(calendar.time).toString()
    }

    fun percentOfTotal() {
        _percentOfTotal.value = if (total.value.isEmpty()) {
            0
        } else {
            (total.value.toLong() * 0.2).roundToLong()
        }.toString()
    }

    fun reversePercentOfTotal() {
        _total.value = if (percentOfTotal.value != "") {
            (percentOfTotal.value.toLong() * 20).toString()
        } else {
            ""
        }
    }

    fun commissionFee() {
        _commissionFee.value = if (total.value.isEmpty()) {
            0
        } else {
            ((percentOfTotal.value.toLong() * 0.17).roundToLong())
        }.toString()
    }

    fun totalLeft() {
        _totalLeft.value = (percentOfTotal.value.toLong() - commissionFee.value.toLong()).toString()
    }

    fun totalReturnAmount() {
        _totalReturnAmount.value = if (winNumberAmount.value.isEmpty()) {
            "0"
        } else {
            (winNumberAmount.value.toLong() * 80).toString()
        }
    }

    fun mustReturnAmount() {
        _mustReturnAmount.value = if (totalReturnAmount.value.isEmpty()) {
            "0"
        } else {
            (totalReturnAmount.value.toLong() * 0.2).roundToLong().toString()
        }
    }

    fun ourTotalProfit() {
        if (totalLeft.value.isEmpty()) {
            "0"
        } else if (totalLeft.value.toLong() < 0) {
            "0"
        } else {
            _ourTotalProfit.value =
                (totalLeft.value.toLong() - mustReturnAmount.value.toFloat()).roundToLong().toString()
        }
    }

    fun profitForShareOwner() {
        _profitForShareOwner.value = if (ourTotalProfit.value.isEmpty()) {
            "0"
        } else if (ourTotalProfit.value.toLong() < 0) {
            (ourTotalProfit.value.toLong() * 0.20).roundToLong().toString()
        } else {
            (ourTotalProfit.value.toLong() * 0.184).roundToLong().toString()
        }
    }

    fun profitForManager() {
        profitForManager.value = if (ourTotalProfit.value.isEmpty()) {
            "0"
        } else if (ourTotalProfit.value.toLong() < 0) {
            "0"
        } else {
            (ourTotalProfit.value.toLong() * 0.08).roundToLong().toString()
        }
    }

    fun getCurrentDateInMilli(): Long {
        return GregorianCalendar.getInstance().timeInMillis
    }

    private fun saveToDb(dataItem: PayOff) {
        viewModelScope.launch {
            repo.addItem(payOff = dataItem)
        }
    }

    private fun deleteItemFromDb(itemId: Long) {
        viewModelScope.launch {
            repo.deleteItem(id = itemId)
        }
    }

    private fun deleteAllItemFromDb() {
        viewModelScope.launch {
            repo.deleteAllItem()
        }
    }

    fun getHistory() {
        viewModelScope.launch {
            repo.getItems().collectLatest {
                Timber.tag(("history again")).d("$it")
                Timber.tag("tzo.title.value").d("${_titleName.value}")
                _result.clear()
                _filterTotal.value = 0
                _realResult.clear()
                when (_titleName.value) {
                    "Today Result" -> {
                        getHistoryToday()
                    }
                    "Yesterday Result" -> {
                        getHistoryYesterday()
                    }
                    "This Week Result" -> {
                        getHistoryThisWeek()
                    }
                    "Last Week Result" -> {
                        getHistoryLastWeek()
                    }
                    "This Month Result" -> {
                        getHistoryThisMonth()
                    }
                    "Last Month Result" -> {
                        getHistoryLastMonth()
                    }
                    "Single Day Result" -> {
                        getHistorySingleDay()
                    }
                    "Date Range Result" -> {
                        getHistoryDateRange()
                    }
                    else -> {
                        _result.addAll(it)
                    }
                }
//                Timber.tag("tzo.output.view").d("$output")
                _result.sortByDescending { it.currentTimeStamp }
            }
        }
    }

    fun calendarDateStart(date: Long): Long {
        val c: Calendar = GregorianCalendar()
        c.timeInMillis = date
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.clear(Calendar.MINUTE)
        c.clear(Calendar.SECOND)
        c.clear(Calendar.MILLISECOND)
        return c.timeInMillis
    }

    fun calendarDateEnd(date: Long): Long {
        val c: Calendar = GregorianCalendar()
        c.timeInMillis = date
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
        return c.timeInMillis
    }

    fun currentDateStart(): Calendar {
        val c: Calendar = GregorianCalendar()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.clear(Calendar.MINUTE)
        c.clear(Calendar.SECOND)
        c.clear(Calendar.MILLISECOND)
        return c
    }

    fun currentDateStartMillis(): Long {
        val c: Calendar = currentDateStart()
        return c.timeInMillis
    }


    fun todayStartMillis(): Long {
        return currentDateStartMillis()
    }

    fun yesterdayStartMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.add(Calendar.DAY_OF_MONTH, -1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        Timber.tag(("start time")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun weekStartMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        Timber.tag(("start week")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun previousWeekStartMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.add(Calendar.WEEK_OF_YEAR, -1)
        c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
//        c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
//        c.add(Calendar.DATE, -7)
        Timber.tag(("start prev week")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun currentMonthStartMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH))
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
//        c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
//        c.add(Calendar.DATE, -7)
        Timber.tag(("start  month")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun previousMonthStartMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.add(Calendar.MONTH, -1)
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH))
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
//        c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
//        c.add(Calendar.DATE, -7)
        Timber.tag(("start  month")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun weekEndMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
        Timber.tag(("end week")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun todayEndMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
        Timber.tag(("end day")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun yesterdayEndMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.add(Calendar.DAY_OF_MONTH, -1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
        Timber.tag(("end  yesterday")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun previousWeekEndMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
        c.add(Calendar.DAY_OF_MONTH, -1)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
        Timber.tag(("end prev week")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun currentMonthEndMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH))
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
//        c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
//        c.add(Calendar.DATE, -7)
        Timber.tag(("end month")).d("${c.timeInMillis}")
        return c.timeInMillis
    }

    fun previousMonthEndMillis(): Long {
        val c: Calendar = GregorianCalendar()
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH))
        c.add(Calendar.DAY_OF_MONTH, -1)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
//        c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
//        c.add(Calendar.DATE, -7)
        Timber.tag(("end month")).d("${c.timeInMillis}")
        return c.timeInMillis
    }


    fun getHistoryWithDate(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            repo.getHistoryWithDate(startDate = startDate, endDate = endDate).collectLatest {
                Timber.tag(("bar nyar day")).d("$it")
                _result.clear()
                _result.addAll(it)
                _result.sortByDescending { it.currentTimeStamp }
            }
        }
    }

    fun getMorningTotalProfitWithDate(startDate: Long, endDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getHistoryWithDate(startDate = calendarDateStart(startDate), endDate = calendarDateEnd(endDate)).collectLatest {
                _resultForManager.clear()
                _resultForManager.addAll(it)
            }
        }
    }

    fun profitForManagerEvening(): String {
        var profitForManager: String = ""
        if(_resultForManager.isEmpty()) {
            profitForManager = if (ourTotalProfit.value.isEmpty()) {
                "0"
            } else if (ourTotalProfit.value.toLong() < 0) {
                "0"
            } else {
                (ourTotalProfit.value.toLong() * 0.08).roundToLong().toString()
            }
        } else {
            val todayTotal = (ourTotalProfit.value.toLong() + _resultForManager.first().totalProfit)
            if(todayTotal<0) {
                profitForManager = "0"
                viewModelScope.launch {
                    repo.updateManagerProfit(managerProfit = 0L, id = _resultForManager.first().id!!)
                }
            } else {
                if(ourTotalProfit.value.toLong()<0 && _resultForManager.first().totalProfit>0) {
                    profitForManager = (todayTotal*0.08).roundToLong().toString()
                    viewModelScope.launch {
                        repo.updateManagerProfit(managerProfit = 0L, id = _resultForManager.first().id!!)
                    }
                }
                else if (ourTotalProfit.value.toLong()>0 && _resultForManager.first().totalProfit<0) {
                    profitForManager = (todayTotal*0.08).roundToLong().toString()
                }
                else if (ourTotalProfit.value.toLong()>0 && _resultForManager.first().totalProfit>0) {
                    profitForManager = (ourTotalProfit.value.toLong()*0.08).roundToLong().toString()
                }
            }
        }
        return profitForManager
    }

    fun profitForManagerMorning(): String {
        var profitForManager: String = ""

        profitForManager = if (ourTotalProfit.value.isEmpty()) {
            "0"
        } else if (ourTotalProfit.value.toLong() < 0) {
            "0"
        } else {
            (ourTotalProfit.value.toLong() * 0.08).roundToLong().toString()
        }
        return profitForManager
    }

    fun historyDateTime(dateInMilli: Long): String {
        val dateFormat = "yyyy MMM dd 'at' hh:mm aa"
        val date = getDate(dateInMilli)
        val skeleton = DateFormat.getBestDateTimePattern(Locale.getDefault(), dateFormat)
        val formatter = SimpleDateFormat(skeleton, Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
            applyLocalizedPattern(skeleton)
        }
        return formatter.format(date.time)
    }

    fun detailDateTime(milli: Long): String {
        val dateFormat = "yyyy MMM dd"
        val date = getDate(milli)
        val skeleton = DateFormat.getBestDateTimePattern(Locale.getDefault(), dateFormat)
        val formatter = SimpleDateFormat(skeleton, Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
            applyLocalizedPattern(skeleton)
        }
        return formatter.format(date.time)
    }

    private fun getDate(timeMilli: Long): Date {
        return Date(timeMilli)
    }

    fun onActionCalculator(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.SaveDataToDb -> {
                _shouldShowCurrent.value = true
                saveToDb(action.data)
                viewModelScope.launch {
                    delay(100L)
                    _titleName.value = "All Time Result"
                    getHistory()
                }
            }
            is CalculatorAction.DeleteDbItem -> {
                deleteItemFromDb(action.itemId)
            }
            CalculatorAction.DeleteAllItem -> {
                deleteAllItemFromDb()
            }
            CalculatorAction.NavigateToHistoryList -> {
                viewModelScope.launch {
                    _calculatorEvent.emit(CalculatorEvent.NavigateToHistoryList)
                }
            }
            is CalculatorAction.switchClick -> {
                viewModelScope.launch {
                    _isMorning.value = action.isMorning
                }
            }
            CalculatorAction.DatePickerClick -> {
                viewModelScope.launch {
                    _calculatorEvent.emit(CalculatorEvent.DatePickerClick)
                }
            }
            is CalculatorAction.ChangeDate -> {
                viewModelScope.launch {
                    _date.value = action.date
                    _dateInMilli.value = action.dateInMilli
                }
            }
            CalculatorAction.ErrorDialogOk -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowErrorDialog = false
                    )
                }
            }
            CalculatorAction.ShowErrorDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowErrorDialog = true
                    )
                }
            }
        }
    }

    fun onActionHistoryList(action: HistoryListAction) {
        when (action) {
            is HistoryListAction.DeleteHistoryItem -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowDialog = false
                    )
                }
                deleteItemFromDb(action.id)
            }
            HistoryListAction.NavigateToCalculator -> {
                viewModelScope.launch {
                    _historyListEvent.emit(HistoryListEvent.NavigateToCalculator)
                }
            }
            is HistoryListAction.ShowDeleteConfirmDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowDialog = true,
                        deleteItem = action.id
                    )
                }
            }
            HistoryListAction.DismissDeleteConfirmDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowDialog = false
                    )
                }
            }
            is HistoryListAction.ShowHistoryDetail -> {
                Timber.tag("tzo.history.detail").d("trigger")
                viewModelScope.launch {
                    _historyListEvent.emit(HistoryListEvent.NavigateToHistoryDetail(id = action.id))
                }
            }
            HistoryListAction.DateFilterDialog -> {
                viewModelScope.launch {
                    _historyListEvent.emit(HistoryListEvent.ShowDateTimeDialog)
                }
            }
            HistoryListAction.FilterDate -> {
                viewModelScope.launch {
                    getHistorySingleDay()
                }
            }
            HistoryListAction.ShowDatePickerDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowDatePickerDialog = true
                    )
                }
            }
            HistoryListAction.HideDatePickerDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowDatePickerDialog = false
                    )
                }
            }

            HistoryListAction.ShowStartDateErrorDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowStartDateErrorDialog = true
                    )
                }
            }
            is HistoryListAction.ShowEndDateErrorDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowEndDateErrorDialog = true,
                        endDateErrorText = action.text
                    )
                }
            }
            HistoryListAction.StartDateErrorDialogOk -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowStartDateErrorDialog = false
                    )
                }
            }
            HistoryListAction.EndDateErrorDialogOk -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowEndDateErrorDialog = false
                    )
                }
            }
            HistoryListAction.DeleteAllDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowDeleteAllDialog = true
                    )
                }
            }
            HistoryListAction.DeleteAllHistoryItem -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowDeleteAllDialog = false
                    )
                    deleteAllItemFromDb()
                }
            }
            HistoryListAction.DismissDeleteAllConfirmDialog -> {
                viewModelScope.launch {
                    _historyListState.value = historyListState.value.copy(
                        shouldShowDeleteAllDialog = false
                    )
                }
            }
        }
    }
}