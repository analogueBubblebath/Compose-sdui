package ml.bubblebath.compose_sdui.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class JsonLayout(
    val header: String,
    val form: JsonForm
)
