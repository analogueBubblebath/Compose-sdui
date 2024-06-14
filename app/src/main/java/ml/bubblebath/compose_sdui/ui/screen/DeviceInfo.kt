package ml.bubblebath.compose_sdui.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ml.bubblebath.compose_ssr.R

@Composable
fun DeviceInfo(isCharging: Boolean, rssi: Int, batteryLevel: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AnimatedVisibility(visible = isCharging) {
            Text(text = stringResource(R.string.charging))
        }
        Text(text = stringResource(R.string.rssi_dbm, rssi))
        Text(text = stringResource(R.string.batteryLevel, batteryLevel))
    }
}
