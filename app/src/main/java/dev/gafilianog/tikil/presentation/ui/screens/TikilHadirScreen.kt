package dev.gafilianog.tikil.presentation.ui.screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gafilianog.tikil.presentation.ui.shared.AdvanceTimePicker
import dev.gafilianog.tikil.presentation.ui.shared.ClickableOutlinedTextField
import dev.gafilianog.tikil.presentation.ui.shared.DatePickerModal
import dev.gafilianog.tikil.presentation.ui.shared.TextFieldInputText
import dev.gafilianog.tikil.presentation.ui.shared.convertMillisToDate
import dev.gafilianog.tikil.presentation.viewmodels.HadirViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun TikilHadirScreen(
    modifier: Modifier = Modifier,
    viewModel: HadirViewModel = hiltViewModel()
) {
    val npp by viewModel.npp.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val clockIn by viewModel.clockIn.collectAsStateWithLifecycle()
    val clockOut by viewModel.clockOut.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val reason by viewModel.reason.collectAsStateWithLifecycle()
    val selectedSpv by viewModel.selectedSpv.collectAsStateWithLifecycle()
    val comment by viewModel.comment.collectAsStateWithLifecycle()

    var showModal by remember { mutableStateOf(false) }
    val spvList = listOf("123 - JOHN DOE", "456 - MAX DOE", "789 - HENRY DOE")
    var isExpanded by remember { mutableStateOf(false) }

    // Time state
    var showClockInPicker by remember { mutableStateOf(false) }
    var showClockOutPicker by remember { mutableStateOf(false) }
    var selectedClockIn by remember { mutableStateOf(LocalTime.now()) }
    var selectedClockOut by remember { mutableStateOf(LocalTime.now()) }

    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
        is24Hour = true
    )

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
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
            value = npp,
            onValueChange = { if (it.length <= 6) viewModel.onNppChange(it) },
            title = "NPP",
            placeholder = "900000",
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardType = KeyboardType.Number
        )

        TextFieldInputText(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            title = "Password",
            placeholder = "asdfgh",
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
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

                ClickableOutlinedTextField(
                    value = clockIn,
                    onValueChange = {},
                    state = selectedClockIn,
                    showComponent = { showClockInPicker = true },
                    placeholder = {},
                    leadingIcon = {
                        Icon(
                            Icons.Filled.AccessTimeFilled,
                            contentDescription = "Clock In",
                            tint = Color.Green
                        )
                    },
                    trailingIcon = {}
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

                ClickableOutlinedTextField(
                    value = clockOut,
                    onValueChange = {},
                    state = selectedClockOut,
                    showComponent = { showClockOutPicker = true },
                    placeholder = { Text(text = "DEFAULT") },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.AccessTimeFilled,
                            contentDescription = "Clock Out",
                            tint = Color.Red
                        )
                    },
                    trailingIcon = {
                        if (clockOut.isNotEmpty()) {
                            IconButton(onClick = {
                                viewModel.onClockOutChange("")
                                showClockOutPicker = false
                            }) {
                                Icon(
                                    Icons.Outlined.Cancel,
                                    contentDescription = "Clear Clock Out"
                                )
                            }
                        }
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
                viewModel.onClockInChange(selectedClockIn.format(DateTimeFormatter.ofPattern("HH:mm")))
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
                viewModel.onClockOutChange(selectedClockOut.format(DateTimeFormatter.ofPattern("HH:mm")))
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
                onDateSelected = viewModel::onSelectedDateChange,
                onDismiss = { showModal = false }
            )
        }

        TextFieldInputText(
            value = reason,
            onValueChange = viewModel::onReasonChange,
            title = "Reason",
            placeholder = "Reason",
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardType = KeyboardType.Text
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
                modifier = Modifier
                    .menuAnchor(type, enabled)
                    .fillMaxWidth(),
                value = selectedSpv,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
            )

            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }) {
                spvList.forEachIndexed { index, spv ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(text = spv) },
                        onClick = {
                            viewModel.onSelectedSpvChange(spvList[index])
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        TextFieldInputText(
            value = comment,
            onValueChange = viewModel::onCommentChange,
            title = "Comment",
            placeholder = "Comment",
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            keyboardType = KeyboardType.Text,
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
