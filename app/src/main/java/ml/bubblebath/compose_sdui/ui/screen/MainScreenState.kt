package ml.bubblebath.compose_sdui.ui.screen

import ml.bubblebath.compose_sdui.model.ui.JsonLayout
import ml.bubblebath.compose_sdui.model.responce.JsonResponse

data class MainScreenState(
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isCharging: Boolean = false,
    val batteryLevel: Int = 100,
    val rssi: Int = -1,
    val layout: JsonLayout? = null,
    val textStates: Map<String, TextState> = emptyMap(),
    val user: JsonResponse? = null
)
