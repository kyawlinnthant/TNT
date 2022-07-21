package mdy.klt.myatmyat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculator(name: String){
    var text by remember { mutableStateOf("10") }
    var percentOfTotal by remember {
        mutableStateOf("0")
    }

    var commissionFee by remember {
        mutableStateOf("0")
    }

    var totalLeft by remember {
        mutableStateOf("0")
    }

    var winNumberAmount by remember {
        mutableStateOf("0")
    }

    var totalReturnAmount by remember {
        mutableStateOf("0")
    }

    var mustReturnAmount by remember {
        mutableStateOf("0")
    }

    var ourTotalProfit by remember {
        mutableStateOf("0")
    }

    var profitForShareOwner by remember {
        mutableStateOf("0")
    }

    var profitForManager by remember {
        mutableStateOf("0")
    }

    percentOfTotal = if (text.isEmpty()) {
        0
    } else {
        (text.toInt() * 0.1).roundToInt()
    }.toString()

    totalReturnAmount = if (winNumberAmount.isEmpty()) {
        "0"
    } else {
        (winNumberAmount.toInt() * 80).toString()
    }

    mustReturnAmount = if (totalReturnAmount.isEmpty()) {
        "0"
    } else {
        (totalReturnAmount.toInt() * 0.1).roundToInt().toString()
    }
    ourTotalProfit = if (totalLeft.isEmpty()) {
        "0"
    } else {
        (totalLeft.toInt() - mustReturnAmount.toFloat()).roundToInt().toString()
    }
    profitForShareOwner = if (ourTotalProfit.isEmpty()) {
        "0"
    } else {
        (ourTotalProfit.toInt() * 0.25).roundToInt().toString()
    }

    profitForManager = if (ourTotalProfit.isEmpty()) {
        "0"
    } else {
        (ourTotalProfit.toInt() * 0.1).roundToInt().toString()
    }

    commissionFee = ((percentOfTotal.toInt() * 0.17).roundToInt()).toString()
    totalLeft = (percentOfTotal.toInt() - commissionFee.toInt()).toString()

    Scaffold(modifier = Modifier, topBar = {
        SmallTopAppBar(
            modifier = Modifier,
            title = { Text(text = "Calculator") },
            colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
        )
    }) {
        Column(modifier = Modifier.padding(it).padding(12.dp)) {
            Text(text = "Lucky $name!", color = MaterialTheme.colorScheme.primary)
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Total Amount")
                CommonTextField(
                    text = text,
                    onValueChange = { value ->
                        text = value
                    },
                    colorScheme = darkColorScheme().copy(
                        primary = MaterialTheme.colorScheme.secondary.copy(0.2f)
                    )
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "10% of Total")
                CommonTextField(
                    text = percentOfTotal,
                    onValueChange = { value ->
                        percentOfTotal = value
                        text = (percentOfTotal.toInt() * 10).toString()
                    },
                    colorScheme = darkColorScheme().copy(
                        primary = MaterialTheme.colorScheme.secondary.copy(0.2f)
                    )
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Commission Fee (17 %)")
                CommonTextField(
                    text = commissionFee,
                    onValueChange = { value ->
                        commissionFee = value
                    },
                    colorScheme = darkColorScheme().copy(primary = MaterialTheme.colorScheme.primaryContainer)
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Our Total Asset(%နှုတ်ပြီးစုစုပေါင်းကျန်ငွေ)")
                CommonTextField(
                    text = totalLeft,
                    onValueChange = { value ->
                        totalLeft = value
                    },
                    colorScheme = darkColorScheme().copy(primary = MaterialTheme.colorScheme.primaryContainer)
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Win number include?(ပေါက်ကြေးစုစုပေါင်း)")
                CommonTextField(
                    text = winNumberAmount,
                    onValueChange = { value ->
                        winNumberAmount = value
                    },
                    colorScheme = darkColorScheme().copy(
                        primary = MaterialTheme.colorScheme.secondary.copy(0.2f)
                    )
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Total return amount(လျော်ကြေးစုစုပေါင်း)")
                CommonTextField(
                    text = totalReturnAmount,
                    onValueChange = { value ->
                        totalReturnAmount = value
                        winNumberAmount = if (totalReturnAmount.isNotEmpty()) {
                            (totalReturnAmount.toInt() / 80).toString()
                        } else {
                            "0"
                        }
                    },
                    colorScheme = darkColorScheme().copy(
                        primary = MaterialTheme.colorScheme.secondary.copy(0.2f)
                    )
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Our return amount?(10%)")
                CommonTextField(
                    text = mustReturnAmount,
                    onValueChange = { value ->
                        mustReturnAmount = value
                        totalReturnAmount = if (mustReturnAmount.isNotEmpty()) {
                            ((mustReturnAmount.toInt() * 10.0)).toString()
                        } else {
                            "0"
                        }
                    },
                    colorScheme = darkColorScheme().copy(primary = MaterialTheme.colorScheme.primaryContainer)
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Our total profit")
                CommonTextField(
                    text = ourTotalProfit,
                    onValueChange = { value ->
                        ourTotalProfit = value
                    },
                    colorScheme = darkColorScheme().copy(primary = MaterialTheme.colorScheme.primaryContainer)
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Share Owner profit")
                CommonTextField(
                    text = profitForShareOwner,
                    onValueChange = { value ->
                        profitForShareOwner = value
                    },
                    colorScheme = darkColorScheme().copy(primary = MaterialTheme.colorScheme.primaryContainer)
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp).weight(1f), verticalAlignment = Alignment.CenterVertically) {
                CommonText(text = "Manager Profit")
                CommonTextField(
                    text = profitForManager,
                    onValueChange = { value ->
                        profitForManager = value
                    },
                    colorScheme = darkColorScheme().copy(primary = MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }
    }
}

private fun percentCalculator(percent: Int) {

}

@Composable
fun CommonTextField(text: String, onValueChange: (String) -> Unit, colorScheme: ColorScheme) {
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        maxLines = 1,
        textStyle = TextStyle(fontSize = 16.sp),
//        modifier = Modifier.background(colorScheme.primary),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(percent = 30))
                    .padding(8.dp)

            ) {
                // <-- Add this
                innerTextField()
            }
        }
    )
}

@Composable
fun CommonText(text: String) {
    Text(
        text = text, modifier = Modifier
            .padding(end = 8.dp)
            .width(220.dp), color = MaterialTheme.colorScheme.primary, fontSize = 16.sp
    )
}