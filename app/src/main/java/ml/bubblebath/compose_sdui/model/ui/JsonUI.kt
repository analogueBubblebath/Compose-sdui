package ml.bubblebath.compose_sdui.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class JsonUI(val activities: List<JsonActivity>)
