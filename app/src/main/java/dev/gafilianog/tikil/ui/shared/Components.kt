package dev.gafilianog.tikil.ui.shared

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun TextFieldInputText(
    keyboardActions: KeyboardActions,
    dataType: String,
    data: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = dataType,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.padding(4.dp))

        OutlinedTextField(
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth(),
            value = data,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvanceTimePicker(
    isShown: Boolean,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    state: TimePickerState,
) {
    var showDial by remember { mutableStateOf(true) }

    val toggleIcon = if (showDial) {
        Icons.Filled.EditCalendar
    } else {
        Icons.Filled.AccessTime
    }

    if (isShown) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = MaterialTheme.colorScheme.surface
                    ),
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        text = title,
                        style = MaterialTheme.typography.labelMedium
                    )
                    if (showDial)
                        TimePicker(state = state)
                    else
                        TimeInput(state = state)
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                    ) {
                        IconButton(onClick = { showDial = !showDial }) {
                            Icon(
                                imageVector = toggleIcon,
                                contentDescription = "Time Picker Type Toggle"
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = onDismiss) { Text(text = "Cancel") }
                        TextButton(onClick = onConfirm) { Text(text = "OK") }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldCredsPreview() {
    var npp by rememberSaveable { mutableStateOf("") }

    TextFieldInputText(
        keyboardActions = KeyboardActions(),
        dataType = "NPP",
        data = npp,
        placeholder = "900000",
        onValueChange = { newVal -> npp = newVal },
        keyboardType = KeyboardType.Number
    )
}