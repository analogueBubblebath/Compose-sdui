package ml.bubblebath.compose_sdui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ml.bubblebath.compose_ssr.R

@Composable
fun UnknownTextField(modifier: Modifier = Modifier) {
    Card(modifier) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.unknown_textfield),
            color = MaterialTheme.colorScheme.error
        )
    }
}