package dev.gafilianog.tikil.ui.screens

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.rememberTextFieldState
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun TikilHadirScreen(
    modifier: Modifier = Modifier
) {
    var npp by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var clockIn by rememberSaveable { mutableStateOf("") }
    var clockOut by rememberSaveable { mutableStateOf("") }
    var reason by rememberSaveable { mutableStateOf("E-Absensi tidak dapat digunakan") }
    var comment by rememberSaveable { mutableStateOf("Mohon approvalnya mas terima kasih") }

    var selectedDate by rememberSaveable { mutableStateOf<Long?>(null) }
    var showModal by rememberSaveable { mutableStateOf(false) }

    val spvList = listOf("123 - JOHN DOE", "456 - MAX DOE")
    var selectedSpv by rememberSaveable { mutableStateOf(spvList[0]) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Tikil Hadir",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "NPP",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = npp,
            onValueChange = { npp = it },
            placeholder = { Text(text = "e.g. 123456") }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "Password",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "e.g. asdfgh") }
        )

        Spacer(modifier = Modifier.padding(4.dp))

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
                    value = clockIn,
                    onValueChange = { clockIn = it },
                    placeholder = { Text(text = "7:25") },
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

        Spacer(modifier = Modifier.padding(4.dp))

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

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "Reason",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = reason,
            onValueChange = { password = it },
            placeholder = { Text(text = "E-Absensi tidak dapat digunakan") }
        )

        Spacer(modifier = Modifier.padding(4.dp))

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

            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                spvList.forEachIndexed { index, spv ->
                    DropdownMenuItem(
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

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "Comment",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = comment,
            onValueChange = { password = it },
            placeholder = { Text(text = "Mohon approvalnya mas terima kasih") }
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