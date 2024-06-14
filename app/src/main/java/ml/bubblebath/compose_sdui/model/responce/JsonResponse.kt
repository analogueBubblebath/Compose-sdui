package ml.bubblebath.compose_sdui.model.responce

import kotlinx.serialization.Serializable

@Serializable
data class JsonResponse(val data: JsonData, val error: JsonError)
