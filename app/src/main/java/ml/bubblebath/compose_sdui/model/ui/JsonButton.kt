package ml.bubblebath.compose_sdui.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class JsonButton(
    val type: String,
    val caption: String,
    val formAction: String
)
