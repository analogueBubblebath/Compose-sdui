package ml.bubblebath.compose_sdui.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import ml.bubblebath.compose_sdui.Constants.UI_ENDPOINT
import ml.bubblebath.compose_sdui.api.SduiApi
import ml.bubblebath.compose_sdui.model.ErrorMessages
import ml.bubblebath.compose_sdui.model.ui.JsonText
import ml.bubblebath.compose_sdui.model.ui.JsonUI
import ml.bubblebath.compose_sdui.model.responce.JsonResponse
import org.koin.core.component.KoinComponent

class MainScreenViewModel(private val api: SduiApi) : ViewModel(), KoinComponent {
    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState

    private fun loadUI() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            val ui = try {
                withContext(Dispatchers.IO) {
                    api.get<JsonUI>(UI_ENDPOINT)
                }
            } catch (e: SerializationException) {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = ErrorMessages.UIDeserialization.description
                )
                return@launch
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = ErrorMessages.UILoadingFailure.description
                )
                return@launch
            }
            val activity = ui.activities.first()
            createTextStates(activity.layout.form.text)
            _uiState.value =
                uiState.value.copy(isError = false, isLoading = false, layout = activity.layout)
        }
    }

    private fun createTextStates(text: List<JsonText>) {
        val textStates = buildMap {
            text.forEach {
                put(it.attribute, TextState(isRequired = it.required, isError = it.required))
            }
        }
        _uiState.value = uiState.value.copy(textStates = textStates)
    }

    fun handleIntent(intent: MainScreenIntent) {
        viewModelScope.launch {
            when (intent) {
                MainScreenIntent.Load -> loadUI()

                is MainScreenIntent.UpdateBatteryStatus -> {
                    _uiState.value = uiState.value.copy(
                        isCharging = intent.isCharging,
                        batteryLevel = intent.level
                    )
                }

                is MainScreenIntent.UpdateWifiStatus -> {
                    _uiState.value = uiState.value.copy(rssi = intent.rssi)
                }

                is MainScreenIntent.TextChange -> {
                    val newMap = uiState.value.textStates.toMutableMap()
                    val textState = newMap[intent.attribute]
                    textState?.let {
                        newMap[intent.attribute] =
                            textState.copy(
                                value = intent.newText,
                                isError = intent.newText.isBlank()
                            )
                        _uiState.value = uiState.value.copy(textStates = newMap)
                    } ?: run {
                        _uiState.value = uiState.value.copy(
                            isError = true,
                            errorMessage = ErrorMessages.LayoutBuildingError.description
                        )
                    }
                }

                is MainScreenIntent.ButtonPressed -> {
                    viewModelScope.launch {
                        try {
                            _uiState.value = uiState.value.copy(isLoading = true)
                            val response = withContext(Dispatchers.IO) {
                                api.get<JsonResponse>(intent.url.removeSuffix("/")) {
                                    uiState.value.textStates.forEach {
                                        parameter(it.key, it.value.value)
                                    }
                                }
                            }
                            _uiState.value = uiState.value.copy(isLoading = false, user = response)
                        } catch (e: SerializationException) {
                            _uiState.value = uiState.value.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = ErrorMessages.UIDeserialization.description
                            )
                        } catch (e: Exception) {
                            _uiState.value = uiState.value.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = ErrorMessages.RequestFailure.description
                            )
                        }
                    }
                }
            }
        }
    }
}
