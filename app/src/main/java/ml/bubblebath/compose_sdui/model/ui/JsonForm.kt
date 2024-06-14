package ml.bubblebath.compose_sdui.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class JsonForm(
    val text: List<JsonText>,
    val buttons: List<JsonButton>
)
