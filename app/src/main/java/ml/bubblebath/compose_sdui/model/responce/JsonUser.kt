package ml.bubblebath.compose_sdui.model.responce

import kotlinx.serialization.Serializable

@Serializable
data class JsonUser(
    val fullName: String,
    val position: String,
    val workHoursInMonth: Int,
    val workedOutHours: Int
)
