package ml.bubblebath.compose_sdui.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ml.bubblebath.compose_ssr.R

@Composable
fun UserCard(
    fullName: String,
    position: String,
    workHoursInMonth: Int,
    workedOutHours: Int
) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = stringResource(R.string.fullname, fullName))
            Text(text = stringResource(R.string.position, position))
            Text(text = stringResource(R.string.workhoursinmonth, workHoursInMonth))
            Text(text = stringResource(R.string.workedouthours, workedOutHours))
        }
    }
}
