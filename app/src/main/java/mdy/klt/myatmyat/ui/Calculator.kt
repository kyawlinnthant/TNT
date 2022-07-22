package mdy.klt.myatmyat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mdy.klt.myatmyat.R
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Calculator(name: String) {
    var total by remember { mutableStateOf("10") }
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
    var passwordVisibility by remember {
        mutableStateOf(true)
    }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_eye_close)
    else painterResource(id = R.drawable.ic_eye_on)


    percentOfTotal = if (total.isEmpty()) {
        0
    } else {
        (total.toInt() * 0.1).roundToInt()
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
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp)
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Lucky $name!",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Close Text",
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                        modifier = Modifier
                            .size(24.dp)
                            .weight(1f)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Total Amount")
                CommonTextField(
                    text = total,
                    onValueChange = { value ->
                        total = value
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "10% of Total")
                CommonTextField(
                    text = percentOfTotal,
                    onValueChange = { value ->
                        percentOfTotal = value
                        total = if (percentOfTotal != "") {
                            (percentOfTotal.toInt() * 10).toString()
                        } else {
                            ""
                        }
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Commission Fee (17 %)")
                CommonTextField(
                    text = commissionFee,
                    onValueChange = { value ->
                        commissionFee = value
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Our Total Asset(%နှုတ်ပြီးစုစုပေါင်းကျန်ငွေ)")
                CommonTextField(
                    text = totalLeft,
                    onValueChange = { value ->
                        totalLeft = value
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Win number include?(ပေါက်ကြေးစုစုပေါင်း)")
                CommonTextField(
                    text = winNumberAmount,
                    onValueChange = { value ->
                        winNumberAmount = value
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Total return amount(လျော်ကြေးစုစုပေါင်း)")
                CommonTextField(
                    text = totalReturnAmount,
                    onValueChange = { value ->
                        totalReturnAmount = value
                        winNumberAmount = if (totalReturnAmount != "") {
                            (totalReturnAmount.toInt() / 80.0).roundToInt().toString()
                        } else {
                            ""
                        }

                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Our return amount?(10%)")
                CommonTextField(
                    text = mustReturnAmount,
                    onValueChange = { value ->
                        mustReturnAmount = value
                        totalReturnAmount = if (mustReturnAmount != "") {
                            (mustReturnAmount.toInt() * 10.0).roundToInt().toString()
                        } else {
                            ""
                        }
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Our total profit")
                CommonTextField(
                    text = ourTotalProfit,
                    onValueChange = { value ->
                        ourTotalProfit = value
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Share Owner profit")
                CommonTextField(
                    text = profitForShareOwner,
                    onValueChange = { value ->
                        profitForShareOwner = value
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Manager Profit")
                CommonTextField(
                    text = profitForManager,
                    onValueChange = { value ->
                        profitForManager = value
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
        }
    }
}

private fun percentCalculator(percent: Int) {

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommonTextField(text: String, onValueChange: (String) -> Unit, passwordVisibility: Boolean) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(percent = 30)
                    )
                    .padding(8.dp)

            ) {
                // <-- Add this
                innerTextField()
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onNext)

//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//        keyboardActions = KeyboardActions(
//            onDone = {
//                keyboardController?.hide()
//                // do something here
//            }
//        )
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