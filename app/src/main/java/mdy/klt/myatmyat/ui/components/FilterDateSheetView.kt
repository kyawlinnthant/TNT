package mdy.klt.myatmyat.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.theme.dimen

@Composable
fun FilterDateSheetView(
    modifier: Modifier = Modifier,
    title: String,
    onItemClick: (moreActionStatus: Int) -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = MaterialTheme.dimen.base_5x
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SheetHeader()
        FilterDateSheetItem(onItemClick = onItemClick)
    }
}

@Composable
@Preview
private fun SheetPreview() {
    Surface {
        FilterDateSheetView(
            title = stringResource(id = R.string.select_date),
            onItemClick = {}
        )
    }
}