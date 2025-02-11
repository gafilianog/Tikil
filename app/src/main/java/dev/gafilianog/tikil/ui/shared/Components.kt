package dev.gafilianog.tikil.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldCreds(
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    dataType: String,
    data: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = dataType,
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth(),
            value = data,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldCredsPreview() {
    var npp by rememberSaveable { mutableStateOf("") }

    TextFieldCreds(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(),
        dataType = "NPP",
        data = npp,
        placeholder = "900000",
        onValueChange = { newVal -> npp = newVal }
    )
}