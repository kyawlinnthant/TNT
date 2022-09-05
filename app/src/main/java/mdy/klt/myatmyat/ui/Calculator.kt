package mdy.klt.myatmyat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.data.PayOff
import mdy.klt.myatmyat.ui.udf.CalculatorAction
import timber.log.Timber
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Calculator(name: String) {

    val vm: MyViewModel = hiltViewModel()
    val history = vm.result

    var total = vm._total.value
    var percentOfTotal = vm._percentOfTotal.value
    var commissionFee = vm._commissionFee.value
    var totalLeft = vm._totalLeft.value
    var winNumberAmount = vm._winNumberAmount.value
    var totalReturnAmount = vm._totalReturnAmount.value
    var mustReturnAmount = vm._mustReturnAmount.value
    var ourTotalProfit = vm._ourTotalProfit.value
    var profitForShareOwner = vm._profitForShareOwner.value
    var profitForManager = vm._profitForManager.value



    val payOff = PayOff(
        timeStamp = 100L,
        totalBalance = 100f,
        netBalance = 100f,
        tnt = 100f,
        each = 100f,
        isMorning = false,
        currentTime = ""
    )



    var passwordVisibility by rememberSaveable {
        mutableStateOf(true)
    }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_eye_close)
    else painterResource(id = R.drawable.ic_eye_on)

    val keyboardController = LocalSoftwareKeyboardController.current



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
                        vm._total.value = value
                        vm.percentOfTotal()
                        vm.commissionFee()
                        vm.totalLeft()
                        vm.ourTotalProfit()
                        vm.profitForShareOwner()
                        vm.profitForManager()
                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "20% of Total")
                CommonTextField(
                    text = percentOfTotal,
                    onValueChange = { value ->
//                        percentOfTotal = value
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
                        vm._commissionFee.value = value
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
                        vm._winNumberAmount.value = value
                        vm.totalReturnAmount()
                        vm.mustReturnAmount()
                        vm.ourTotalProfit()
                        vm.profitForShareOwner()
                        vm.profitForManager()
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
//                        winNumberAmount = if (totalReturnAmount != "") {
//                            (totalReturnAmount.toInt() / 80.0).roundToInt().toString()
//                        } else {
//                            ""
//                        }

                    },
                    passwordVisibility = passwordVisibility,
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Our return amount?(20%)")
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
                        vm._profitForShareOwner.value = value
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
                        vm._profitForManager.value = value
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
                        vm.onActionCalculator(
                            action = CalculatorAction.SaveDataToDb(
                                data = payOff.copy(totalBalance = ourTotalProfit.toFloat(), currentTime = vm.getCurrentDateTime().toString())
                            )
                        )
                        Timber.tag("Click Event").d("Save")
                    }
                ) {
                    Text(text = "Save to DB")
                }
                OutlinedButton(
                    onClick = {
                        vm.onActionCalculator(action = CalculatorAction.DeleteDbItem(itemId = 1L))
                    }
                ) {
                    Text(text = "Delete item from DB")
                }
                OutlinedButton(
                    onClick = {
                        vm.onActionCalculator(action = CalculatorAction.DeleteAllItem)
                    }
                ) {
                    Text(text = "All")
                }
//                    Log.d("Log from db", "${_payOff}")
            }

            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(history) { result ->
                    Text(text = result.toString())
                }
            }
        }
    }
}

@Composable
fun ShowText(text: String) {
    Text(text = text)
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