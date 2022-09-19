package mdy.klt.myatmyat.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import mdy.klt.myatmyat.theme.dimen


@Composable
fun OtpTextFieldSection(
    modifier: Modifier = Modifier,
    length: Int,
    onFilled: (code: String) -> Unit,
    isError: Boolean,
) {

    var code: List<Char> by remember { mutableStateOf(listOf()) }
    val focusRequesters: List<FocusRequester> = remember {
        val focusIndicator = mutableListOf<FocusRequester>()
        repeat(length) {
            focusIndicator.add(FocusRequester())
        }
        focusIndicator
    }


    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base_2x)
        ) {
            (0 until length).forEach { index ->
                val requester = focusRequesters[index]
                val inputCode =
                    code.getOrNull(index = index)?.takeIf { it.isDigit() }?.toString() ?: ""


                OtpTextField(
                    focusRequester = requester,
                    code = inputCode,
                    onValueChanged = { value: String ->
                        if (focusRequesters[index].freeFocus()) {
                            val tempCode = code.toMutableList()
                            if (value.isEmpty() || value == "") {
                                if (tempCode.size > index) {
                                    tempCode.removeAt(index = index)
                                    code = tempCode
                                    focusRequesters.getOrNull(index - 1)?.requestFocus()
                                }
                            } else {
                                if (code.size > index) {
                                    tempCode[index] = value.getOrNull(0) ?: ' '
                                } else {
                                    tempCode.add(value.getOrNull(0) ?: ' ')
                                    code = tempCode
                                    focusRequesters.getOrNull(index + 1)?.requestFocus()
                                        ?: onFilled(
                                            code.joinToString(separator = "")
                                        )
                                }
                            }
                        }
                    },
                    isError = isError,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpTextField(
    focusRequester: FocusRequester,
    code: String,
    onValueChanged: (String) -> Unit,
    isError: Boolean
) {
    OutlinedTextField(
        modifier = Modifier
            .size(MaterialTheme.dimen.base_8x)
//            .clip(RoundedCornerShape(MaterialTheme.dimen.base))
            .focusRequester(
                focusRequester = focusRequester
            ),
//        shape = RoundedCornerShape(MaterialTheme.dimen.base_2x),

        textStyle = MaterialTheme.typography.titleLarge.copy(
            textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        ),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "*",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                fontWeight = FontWeight.Bold
            )
        },
        singleLine = true,
        value = code,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
        )
    )

}
