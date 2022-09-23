package mdy.klt.myatmyat.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import mdy.klt.myatmyat.theme.dimen
import mdy.klt.myatmyat.ui.MyViewModel
import mdy.klt.myatmyat.ui.udf.HistoryListAction
import java.util.*

@Composable
fun DateDialog(
               vm: MyViewModel,
               disMissDialog : () -> Unit = {}
) {

    val context = LocalContext.current
    /** date picker */
    val startDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

            val calendar = GregorianCalendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val fullDateInMilli = calendar.timeInMillis
            vm.onActionHistoryList(
                action = HistoryListAction.FilterDate(date = fullDateInMilli)
            )
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    )

    val endDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

            val calendar = GregorianCalendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val fullDateInMilli = calendar.timeInMillis
            vm.onActionHistoryList(
                action = HistoryListAction.FilterDate(date = fullDateInMilli)
            )
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    )

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        icon = {
            Icon(Icons.Filled.DateRange, contentDescription = "Date Icon")
        },
        title = {
            Text(text = "Select Date Range")
        },
        text = {
            Row(
                modifier = Modifier.padding(top = MaterialTheme.dimen.base_2x),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base_2x)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Start Date",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        onClick = { startDatePickerDialog.show() },
                    ) {
                        Text(text = "19/09/2022")
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "End Date",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        onClick = { endDatePickerDialog.show() },
                    ) {
                        Text(text = "19/09/2022")
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
            }
        },
        dismissButton = {
            Button(onClick = {
             disMissDialog()
            }) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Done",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
    )

}