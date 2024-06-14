package ml.bubblebath.compose_sdui.ui.screen

import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ml.bubblebath.compose_sdui.composable.AutocompleteTextField
import ml.bubblebath.compose_sdui.composable.UnknownTextField
import ml.bubblebath.compose_sdui.model.ui.TextType
import ml.bubblebath.compose_sdui.receiver.BatteryBroadcastReceiver
import ml.bubblebath.compose_sdui.receiver.WifiBroadcastReceiver
import ml.bubblebath.compose_ssr.BuildConfig
import ml.bubblebath.compose_ssr.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier) {
    val context = LocalContext.current
    val viewModel = koinViewModel<MainScreenViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.handleIntent(MainScreenIntent.Load)
    }

    if (uiState.isLoading) {
        Box(modifier = modifier) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        if (uiState.isError) {
            AppError(modifier, uiState.errorMessage, onRetryClick = {
                viewModel.handleIntent(MainScreenIntent.Load)
            })
        } else {
            uiState.layout?.let { layout ->
                Scaffold(modifier = modifier.padding(top = 52.dp), topBar = {
                    CenterAlignedTopAppBar(title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = layout.header)
                            Text(text = BuildConfig.VERSION_NAME)
                        }
                    })
                }) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DeviceInfo(uiState.isCharging, uiState.rssi, uiState.batteryLevel)

                        layout.form.text.forEach { jsonText ->
                            when (TextType.getByJsonType(jsonText.type)) {
                                TextType.PLAIN -> {
                                    uiState.textStates[jsonText.attribute]?.let {
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 16.dp, end = 16.dp),
                                            value = it.value,
                                            label = { Text(text = jsonText.caption) },
                                            isError = it.isError,
                                            supportingText = {
                                                if (it.isError) {
                                                    Text(stringResource(R.string.required_field))
                                                }
                                            },
                                            onValueChange = { newText ->
                                                viewModel.handleIntent(
                                                    MainScreenIntent.TextChange(
                                                        attribute = jsonText.attribute,
                                                        newText = newText
                                                    )
                                                )
                                            })
                                    }
                                }

                                TextType.AUTOCOMPLETE -> {
                                    uiState.textStates[jsonText.attribute]?.let {
                                        AutocompleteTextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 16.dp, end = 16.dp),
                                            label = { Text(text = jsonText.caption) },
                                            value = it.value,
                                            isError = it.isError,
                                            suggestionValues = jsonText.suggestions ?: emptyList(),
                                            supportingText = {
                                                if (it.isError) {
                                                    Text(stringResource(id = R.string.required_field))
                                                }
                                            },
                                            onValueChange = { newText ->
                                                viewModel.handleIntent(
                                                    MainScreenIntent.TextChange(
                                                        attribute = jsonText.attribute,
                                                        newText = newText
                                                    )
                                                )
                                            },
                                            onSuggestedValueClick = { newText ->
                                                viewModel.handleIntent(
                                                    MainScreenIntent.TextChange(
                                                        attribute = jsonText.attribute,
                                                        newText = newText
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }

                                TextType.UNKNOWN -> {
                                    UnknownTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, end = 16.dp)
                                    )
                                }
                            }
                        }
                        layout.form.buttons.forEach {
                            Button(
                                enabled = !uiState.textStates.values.any { it.isError },
                                modifier = Modifier.padding(start = 16.dp),
                                onClick = { viewModel.handleIntent(MainScreenIntent.ButtonPressed(it.formAction)) }) {
                                Text(text = it.caption)
                            }
                        }

                        uiState.user?.let {
                            if (it.error.isError) {
                                ServerError(it.error.description)
                            } else {
                                with(it.data.user) {
                                    UserCard(fullName, position, workHoursInMonth, workedOutHours)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(key1 = context) {
        val batteryReceiver = BatteryBroadcastReceiver { isCharging, level ->
            viewModel.handleIntent(MainScreenIntent.UpdateBatteryStatus(isCharging, level))
        }
        val wifiReceiver = WifiBroadcastReceiver { rssi ->
            viewModel.handleIntent(MainScreenIntent.UpdateWifiStatus(rssi))
        }

        context.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        context.registerReceiver(wifiReceiver, IntentFilter(WifiManager.RSSI_CHANGED_ACTION))

        onDispose {
            context.unregisterReceiver(batteryReceiver)
            context.unregisterReceiver(wifiReceiver)
        }
    }
}
