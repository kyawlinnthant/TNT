package mdy.klt.myatmyat.ui.udf

sealed class HistoryListEvent {
    object NavigateToCalculator: HistoryListEvent()
    data class NavigateToHistoryDetail(val id: Long): HistoryListEvent()
}
