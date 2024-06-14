package ml.bubblebath.compose_sdui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

class BatteryBroadcastReceiver(
    private val onBatteryChangeCallback: (isCharging: Boolean, level: Int) -> Unit
) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
        onBatteryChangeCallback(isCharging, level)
    }
}
