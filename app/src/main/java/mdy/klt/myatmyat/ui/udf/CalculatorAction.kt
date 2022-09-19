package mdy.klt.myatmyat.ui.udf

import mdy.klt.myatmyat.data.PayOff

sealed class CalculatorAction {
 data class SaveDataToDb(val data: PayOff) : CalculatorAction()
 data class DeleteDbItem(val itemId: Long) : CalculatorAction()
 data class switchClick(val isMorning: Boolean) : CalculatorAction()
 object NavigateToHistoryList : CalculatorAction()
 object DeleteAllItem : CalculatorAction()
}

