package mdy.klt.myatmyat.ui

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mdy.klt.myatmyat.data.PayOff
import mdy.klt.myatmyat.repository.HistoryRepository
import mdy.klt.myatmyat.ui.udf.CalculatorAction
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repo: HistoryRepository
) : ViewModel() {
    private val _result = mutableStateListOf<PayOff>()
    val result: List<PayOff> get() = _result

    val _percentOfTotal = mutableStateOf("0")
    private val percentOfTotal: MutableState<String> get() = _percentOfTotal

    val _commissionFee = mutableStateOf("0")
    private val commissionFee: MutableState<String> get() = _commissionFee

    val _totalLeft = mutableStateOf("0")
    private val totalLeft: MutableState<String> get() = _totalLeft

    val _winNumberAmount = mutableStateOf("0")
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



    var realResult : MutableList<PayOff> = mutableStateListOf()

    val _total = mutableStateOf("0")
    val total: MutableState<String> get() = _total

    init {
        getHistory()
    }

    fun percentOfTotal() {
        _percentOfTotal.value = if (total.value.isEmpty()) {
            0
        } else {
            (total.value.toInt() * 0.2).roundToInt()
        }.toString()
    }

    fun reversePercentOfTotal() {
       _total.value = if (percentOfTotal.value != "") {
            (percentOfTotal.value.toInt() * 20).toString()
        } else {
            ""
        }
    }

    fun commissionFee() {
        _commissionFee.value = if (total.value.isEmpty()) {
            0
        } else {
            ((percentOfTotal.value.toInt() * 0.17).roundToInt())
        }.toString()
    }

    fun totalLeft() {
        _totalLeft.value = (percentOfTotal.value.toInt() - commissionFee.value.toInt()).toString()
    }

    fun totalReturnAmount() {
        _totalReturnAmount.value = if (winNumberAmount.value.isEmpty()) {
            "0"
        } else {
            (winNumberAmount.value.toInt() * 80).toString()
        }
    }

    fun mustReturnAmount() {
        _mustReturnAmount.value = if (totalReturnAmount.value.isEmpty()) {
            "0"
        } else {
            (totalReturnAmount.value.toInt() * 0.2).roundToInt().toString()
        }
    }

    fun ourTotalProfit() {
        if (totalLeft.value.isEmpty()) {
            "0"
        } else if (totalLeft.value.toInt() < 0) {
            "0"
        } else {
            _ourTotalProfit.value = (totalLeft.value.toInt() - mustReturnAmount.value.toFloat()).roundToInt().toString()
        }
    }

    fun profitForShareOwner() {
        _profitForShareOwner.value = if (ourTotalProfit.value.isEmpty()) {
            "0"
        } else {
            (ourTotalProfit.value.toInt() * 0.184).roundToInt().toString()
        }
    }

    fun profitForManager() {
        _profitForManager.value = if (ourTotalProfit.value.isEmpty()) {
            "0"
        } else {
            (ourTotalProfit.value.toInt() * 0.08).roundToInt().toString()
        }
    }

    fun getCurrentDateTime():Long{
      return Calendar.getInstance().timeInMillis
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

    private fun getHistory() {
        viewModelScope.launch {
            repo.getItems().collectLatest {
                _result.clear()
                _result.addAll(it)
            }
        }
    }

    fun onActionCalculator(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.SaveDataToDb -> {
                saveToDb(action.data)
            }
            is CalculatorAction.DeleteDbItem -> {
                deleteItemFromDb(action.itemId)
            }
            CalculatorAction.DeleteAllItem -> {
                deleteAllItemFromDb()
            }
        }
    }
}