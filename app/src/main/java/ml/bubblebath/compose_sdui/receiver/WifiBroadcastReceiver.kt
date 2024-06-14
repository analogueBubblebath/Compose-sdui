package ml.bubblebath.compose_sdui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager

class WifiBroadcastReceiver(private val onRssiChangedCallback: (rssi: Int) -> Unit) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val rssi = intent?.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -1) ?: -1
        onRssiChangedCallback(rssi)
    }
}
