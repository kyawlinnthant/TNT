package mdy.klt.myatmyat.ui.udf

import mdy.klt.myatmyat.data.PayOff

sealed class CalculatorAction {
 data class SaveDataToDb(val data: PayOff) : CalculatorAction()
 data class DeleteDbItem(val itemId: Long) : CalculatorAction()
 object DeleteAllItem : CalculatorAction()
}

