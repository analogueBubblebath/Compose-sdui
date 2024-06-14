package ml.bubblebath.compose_sdui.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class JsonText(
    val type: String,
    val caption: String,
    val attribute: String,
    val required: Boolean,
    val suggestions: List<String>? = null
)
