package mdy.klt.myatmyat.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.data.PayOff
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Calculator(name: String) {
    var total by remember { mutableStateOf("10") }
    val vm: MyViewModel = hiltViewModel()
    var logPayOff: Flow<List<PayOff>> = emptyFlow()
    var id: MutableState<String> = remember {mutableStateOf("")}
    var _payOff: MutableState<PayOff> = remember {
        mutableStateOf(
            PayOff(
                id = 100L,
                timeStamp = 100L,
                totalBalance = 100f,
                netBalance = 100f,
                tnt = 100f,
                each = 100f,
                isMorning = false
            )
        )
    }

//    val payOff: MutableState<PayOff> get()
    var percentOfTotal by rememberSaveable {
        mutableStateOf("0")
    }

    var commissionFee by rememberSaveable {
        mutableStateOf("0")
    }

    var totalLeft by rememberSaveable {
        mutableStateOf("0")
    }

    var winNumberAmount by rememberSaveable {
        mutableStateOf("0")
    }

    var totalReturnAmount by rememberSaveable {
        mutableStateOf("0")
    }

    var mustReturnAmount by rememberSaveable {
        mutableStateOf("0")
    }

    var ourTotalProfit by rememberSaveable {
        mutableStateOf("0")
    }

    var profitForShareOwner by rememberSaveable {
        mutableStateOf("0")
    }

    var profitForManager by rememberSaveable {
        mutableStateOf("0")
    }

    var passwordVisibility by rememberSaveable {
        mutableStateOf(true)
    }

    var focusState by rememberSaveable {
        mutableStateOf(true)
    }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_eye_close)
    else painterResource(id = R.drawable.ic_eye_on)

    val keyboardController = LocalSoftwareKeyboardController.current


    percentOfTotal = if (total.isEmpty()) {
        0
    } else {
        (total.toInt() * 0.2).roundToInt()
    }.toString()

    totalReturnAmount = if (winNumberAmount.isEmpty()) {
        "0"
    } else {
        (winNumberAmount.toInt() * 80).toString()
    }

    mustReturnAmount = if (totalReturnAmount.isEmpty()) {
        "0"
    } else {
        (totalReturnAmount.toInt() * 0.2).roundToInt().toString()
    }

    if (totalLeft.isEmpty()) {
        "0"
    } else if (totalLeft.toInt() < 0) {
        "0"
    } else {
        (totalLeft.toInt() - mustReturnAmount.toFloat()).roundToInt().toString()
    }
    profitForShareOwner = if (ourTotalProfit.isEmpty()) {
        "0"
    } else {
        (ourTotalProfit.toInt() * 0.184).roundToInt().toString()
    }

    profitForManager = if (ourTotalProfit.isEmpty()) {
        "0"
    } else {
        (ourTotalProfit.toInt() * 0.08).roundToInt().toString()
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        keyboardController?.hide()
                    }),
        ) {
            Row(modifier = Modifier.weight(0.5f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Lucky $name!",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(0.5f)
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
                            (mustReturnAmount.toInt() * 20.0).roundToInt().toString()
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
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        vm.viewModelScope.launch {
                            vm.saveToDb(
                                _payOff.value.copy(
                                    id = 100L,
                                    timeStamp = 10000L,
                                    totalBalance = ourTotalProfit.toFloat(),
                                    netBalance = 100f,
                                    tnt = 100f,
                                    each = 100f,
                                    isMorning = true
                                )
                            )
                            logPayOff = vm.getFromDb()
                            vm.viewModelScope.launch {
                                logPayOff.collect{
                                        it ->
                                    it.forEach { save ->
                                        id.value = save.totalBalance.toString()
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Text(text = "Save to DB")
                }
                    Log.d("Log from db", "${_payOff}")
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = id.value)
            }
        }
    }
}

private fun percentCalculator(percent: Int) {

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommonTextField(text: String, onValueChange: (String) -> Unit, passwordVisibility: Boolean) {
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