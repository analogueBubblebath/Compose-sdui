package ml.bubblebath.compose_sdui.ui.screen


sealed interface MainScreenIntent {
    data object Load : MainScreenIntent
    data class UpdateBatteryStatus(val isCharging: Boolean, val level: Int) : MainScreenIntent
    data class UpdateWifiStatus(val rssi: Int) : MainScreenIntent
    data class TextChange(val attribute: String, val newText: String) : MainScreenIntent
    data class ButtonPressed(val url: String) : MainScreenIntent
}
