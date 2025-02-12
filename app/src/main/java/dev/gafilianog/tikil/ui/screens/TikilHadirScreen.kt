package dev.gafilianog.tikil.ui.screens

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gafilianog.tikil.ui.shared.AdvanceTimePicker
import dev.gafilianog.tikil.ui.shared.TextFieldInputText
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun TikilHadirScreen(
    modifier: Modifier = Modifier
) {
    var npp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var clockIn by remember { mutableStateOf("") }
    var clockOut by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("E-Absensi tidak dapat digunakan") }
    var comment by remember { mutableStateOf("Mohon approvalnya mas terima kasih") }

    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    val spvList = listOf("123 - JOHN DOE", "456 - MAX DOE", "789 - HENRY DOE")
    var selectedSpv by remember { mutableStateOf(spvList[0]) }
    var isExpanded by remember { mutableStateOf(false) }

    // Time state
    var showClockInPicker by remember { mutableStateOf(false) }
    var showClockOutPicker by remember { mutableStateOf(false) }
    var selectedClockIn by remember { mutableStateOf(LocalTime.now()) }
    var selectedClockOut by remember { mutableStateOf(LocalTime.now()) }
//    var showDial by remember { mutableStateOf(true) }

    val timePickerState = rememberTimePickerState(
        initialHour = selectedClockIn.hour,
        initialMinute = selectedClockIn.minute,
        is24Hour = true
    )

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Tikil",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.padding(4.dp))

        TextFieldInputText(
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            dataType = "NPP",
            data = npp,
            placeholder = "900000",
            onValueChange = { if (it.length <= 6) npp = it },
            keyboardType = KeyboardType.Number
        )

        TextFieldInputText(
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            dataType = "Password",
            data = password,
            placeholder = "asdfgh",
            onValueChange = { password = it },
            keyboardType = KeyboardType.Password
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Clock In",
                    style = MaterialTheme.typography.bodyLarge
                )

                OutlinedTextField(
                    value = selectedClockIn.format(DateTimeFormatter.ofPattern("HH:mm")),
                    onValueChange = {},
                    modifier = Modifier
                        .pointerInput(selectedClockIn) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null)
                                    showClockInPicker = true
                            }
                        },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.AccessTimeFilled,
                            contentDescription = "Clock In",
                            tint = Color.Green
                        )
                    }
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Clock Out",
                    style = MaterialTheme.typography.bodyLarge
                )

                OutlinedTextField(
                    value = clockOut,
                    onValueChange = { clockOut = it },
                    placeholder = { Text(text = "DEFAULT") },
                    modifier = Modifier
                        .pointerInput(selectedClockIn) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null)
                                    showClockOutPicker = true
                            }
                        },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.AccessTimeFilled,
                            contentDescription = "Clock Out",
                            tint = Color.Red
                        )
                    }
                )
            }
        }

        AdvanceTimePicker(
            isShown = showClockInPicker,
            title = "Clock In",
            onDismiss = { showClockInPicker = false },
            onConfirm = {
                selectedClockIn = LocalTime.of(
                    timePickerState.hour,
                    timePickerState.minute
                )
                showClockInPicker = false
            },
            state = timePickerState
        )

        AdvanceTimePicker(
            isShown = showClockOutPicker,
            title = "Clock Out",
            onDismiss = { showClockOutPicker = false },
            onConfirm = {
                selectedClockOut = LocalTime.of(
                    timePickerState.hour,
                    timePickerState.minute
                )
                showClockOutPicker = false
            },
            state = timePickerState
        )

        Text(
            text = "Date",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = {},
            placeholder = { Text(text = "DD/MM/YYYY") },
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "Select Date")
            },
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(selectedDate) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null)
                            showModal = true
                    }
                }
        )
        
        if (showModal) {
            DatePickerModal(
                onDateSelected = { selectedDate = it },
                onDismiss = { showModal = false }
            )
        }

        TextFieldInputText(
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            dataType = "Reason",
            data = reason,
            placeholder = "E-Absensi tidak dapat digunakan",
            onValueChange = { reason = it }
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = "Supervisor",
            style = MaterialTheme.typography.bodyLarge
        )

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                value = selectedSpv,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
            )

            ExposedDropdownMenu(modifier = Modifier.fillMaxWidth(), expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                spvList.forEachIndexed { index, spv ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(text = spv) },
                        onClick = {
                            selectedSpv = spvList[index]
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        TextFieldInputText(
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            dataType = "Comment",
            data = comment,
            placeholder = "Mohon approvalnya mas terima kasih",
            onValueChange = { comment = it },
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}