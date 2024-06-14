package ml.bubblebath.compose_sdui.model.responce

import kotlinx.serialization.Serializable

@Serializable
data class JsonError(val description: String, val isError: Boolean)
