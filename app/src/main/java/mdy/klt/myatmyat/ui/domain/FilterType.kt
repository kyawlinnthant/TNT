package mdy.klt.myatmyat.ui.domain

enum class FilterType(val select: Int) {
    ALL_TIME(0),
    TODAY(1),
    YESTERDAY(2),
    THIS_WEEK(3),
    LAST_WEEK(4),
    THIS_MONTH(5),
    LAST_MONTH(6),
    SINGLE_DAY(7),
    DATE_RANGE(8)
}