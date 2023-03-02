package mdy.klt.myatmyat.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    textFieldLabel: String,
    text: String,
    onValueChange: (String) -> Unit,
    passwordVisibility: Boolean,
    isError: Boolean
) {
    val pattern = remember { Regex("^(0|[1-9]\\d*)\$") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            if (it.length <= 15 && it.matches(pattern) || it.isEmpty()) {
                onValueChange(it)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        label = { Text(textFieldLabel) },
        singleLine = true,
        isError = isError
    )

}