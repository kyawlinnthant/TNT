package mdy.klt.myatmyat.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mdy.klt.myatmyat.theme.dimen
import mdy.klt.myatmyat.R.drawable
import mdy.klt.myatmyat.R.string
import mdy.klt.myatmyat.ui.domain.FilterType

@Composable
fun FilterDateSheetItem(onItemClick: (moreActionStatus: Int) -> Unit) {

    LazyColumn(
        content = {
            itemsIndexed(items = filterDateData) { index, item ->
                when (index) {
                    FilterType.All_TIME.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.All_TIME.select)
                            },
                        )
                    }
                    FilterType.TODAY.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.TODAY.select)
                            },
                        )
                    }
                    FilterType.YESTERDAY.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.YESTERDAY.select)
                            },
                        )
                    }
                    FilterType.THIS_WEEK.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.THIS_WEEK.select)
                            },
                        )
                    }
                    FilterType.LAST_WEEK.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.LAST_WEEK.select)
                            },
                        )
                    }
                    FilterType.THIS_MONTH.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.THIS_MONTH.select)
                            },
                        )
                    }
                    FilterType.LAST_MONTH.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.LAST_MONTH.select)
                            },
                        )
                    }
                    FilterType.SINGLE_DAY.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.SINGLE_DAY.select)
                            },
                        )
                    }
                    FilterType.DATE_RANGE.select -> {
                        FilterDateItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(FilterType.DATE_RANGE.select)
                            },
                        )
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDateItemContent(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    onItemClick: () -> Unit,
    itemColor: Color = MaterialTheme.colorScheme.onSurface
) {
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(MaterialTheme.dimen.base))
            .clickable(
                onClick = onItemClick
            ),
        leadingContent = {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = "Leading Content",
            )
        },
        headlineText = {
            Text(
                text = stringResource(id = text),
            )
        },
        colors = ListItemDefaults.colors(
            headlineColor = itemColor,
            leadingIconColor = itemColor
        )
    )
}

private val filterDateData = mutableListOf(
    drawable.ic_history to string.all_time,
    drawable.ic_history to string.today,
    drawable.ic_history to string.yesterday,
    drawable.ic_history to string.this_week,
    drawable.ic_history to string.last_week,
    drawable.ic_history to string.this_month,
    drawable.ic_history to string.last_month,
    drawable.ic_history to string.single_day,
    drawable.ic_history to string.date_range,
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true)
@Composable
fun FilterDateSheetItemPreview() {
    FilterDateSheetItem(onItemClick = {})
}