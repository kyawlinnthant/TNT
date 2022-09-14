package mdy.klt.myatmyat.ui.udf

sealed class HistoryListAction {
    object NavigateToCalculator : HistoryListAction()
    object DismissDeleteConfirmDialog : HistoryListAction()
    data class ShowDeleteConfirmDialog(val id: Long) : HistoryListAction()
    data class ShowHistoryDetail(val id: Long) : HistoryListAction()
    data class DeleteHistoryItem(val id: Long) : HistoryListAction()
}
