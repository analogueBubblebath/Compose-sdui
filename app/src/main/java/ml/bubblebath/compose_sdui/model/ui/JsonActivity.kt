package ml.bubblebath.compose_sdui.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class JsonActivity(
    val activity: String,
    val layout: JsonLayout
)
