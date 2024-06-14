package ml.bubblebath.compose_sdui.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ml.bubblebath.compose_ssr.R

@Composable
fun AppError(
    modifier: Modifier,
    errorMessage: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = Icons.Filled.Warning,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = null
        )
        Text(text = stringResource(R.string.error))
        Text(text = errorMessage)
        Button(onClick = onRetryClick) {
            Text(text = stringResource(R.string.retry))
        }
    }
}
