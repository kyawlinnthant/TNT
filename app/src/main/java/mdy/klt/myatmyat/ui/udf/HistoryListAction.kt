package mdy.klt.myatmyat.ui.udf

sealed class HistoryListAction {
    object NavigateToCalculator : HistoryListAction()
    object DateFilterDialog : HistoryListAction()
    object ShowDatePickerDialog : HistoryListAction()
    object HideDatePickerDialog: HistoryListAction()
    object DismissDeleteConfirmDialog : HistoryListAction()
    object DismissDeleteAllConfirmDialog : HistoryListAction()
    object ShowStartDateErrorDialog : HistoryListAction()
    data class ShowEndDateErrorDialog(val text: String) : HistoryListAction()
    object StartDateErrorDialogOk : HistoryListAction()
    object EndDateErrorDialogOk : HistoryListAction()
    data class ShowDeleteConfirmDialog(val id: Long) : HistoryListAction()
    data class ShowHistoryDetail(val id: Long) : HistoryListAction()
    data class DeleteHistoryItem(val id: Long) : HistoryListAction()
    object DeleteAllHistoryItem : HistoryListAction()
    object FilterDate : HistoryListAction()
    object DeleteAllDialog : HistoryListAction()
}
