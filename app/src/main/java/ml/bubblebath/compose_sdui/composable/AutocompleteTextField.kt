package ml.bubblebath.compose_sdui.composable

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    suggestionValues: List<String> = emptyList(),
    onSuggestedValueClick: (String) -> Unit,
    supportingText: @Composable () -> Unit,
    label: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {}
    ) {
        OutlinedTextField(
            modifier = modifier.menuAnchor(),
            value = value,
            isError = isError,
            onValueChange = {
                isExpanded = it.isNotBlank() && suggestionValues.any { suggestion ->
                    suggestion.contains(value, ignoreCase = true)
                }
                onValueChange(it)
            },
            label = label,
            supportingText = supportingText
        )
        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            suggestionValues.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSuggestedValueClick(it)
                    isExpanded = false
                })
            }
        }
    }
}