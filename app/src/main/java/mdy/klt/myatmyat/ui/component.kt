package mdy.klt.myatmyat.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties

enum class ButtonType {
    SOLID_BUTTON,
    TONAL_BUTTON,
    TEXT_BUTTON
}

@Composable
fun CommonDialog(
    modifier: Modifier,
    title: String = "",
    text: String = "",

    dismissButtonLabel: String = "",
    dismissAction: () -> Unit = {},

    confirmButtonLabel: String = "",
    confirmButtonType: ButtonType? = null,
    confirmButtonColors: Color,
    confirmButtonLabelColors: Color,
    confirmButtonAction: () -> Unit = {}

) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = { dismissAction() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        title = {
            if (title.isNotEmpty()) {
                Text(text = title)
            }
        },
        text = {
            if (text.isNotEmpty()) {
                Text(text = text)
            }
        },
        dismissButton = {
            if (dismissButtonLabel.isNotEmpty()) {
                TextButton(onClick = { dismissAction() }) {
                    Text(
                        text = dismissButtonLabel,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        confirmButton = {
            confirmButtonType?.let {
                when (confirmButtonType) {
                    ButtonType.SOLID_BUTTON -> {
                        confirmButtonLabel?.let {
                            Button(
                                onClick = confirmButtonAction,
                                colors = ButtonDefaults.buttonColors(confirmButtonColors)
                            ) {
                                Text(
                                    text = confirmButtonLabel,
                                    color = confirmButtonLabelColors
                                )
                            }
                        }
                    }
                    ButtonType.TONAL_BUTTON -> {
                        if (confirmButtonLabel.isNotEmpty()) {
                            FilledTonalButton(onClick = { confirmButtonAction() }) {
                                Text(text = confirmButtonLabel)
                            }
                        }
                    }
                    ButtonType.TEXT_BUTTON -> { }
                }
            }
        },
    )

}

@Composable
@Preview(showBackground = true)
fun CommonDialogPreview() {
    CommonDialog(
        modifier = Modifier,
        title = "Hello",
        text = "Love of my Life min galar maung nakja kfaf fdkafdaskj ?",
        dismissButtonLabel = "",
        dismissAction = {},
        confirmButtonAction = {},
        confirmButtonColors = MaterialTheme.colorScheme.error,
        confirmButtonLabel = "Comfirm",
        confirmButtonType = ButtonType.TONAL_BUTTON,
        confirmButtonLabelColors = MaterialTheme.colorScheme.error
    )
}