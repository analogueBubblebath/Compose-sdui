package ml.bubblebath.compose_sdui.model.ui

enum class TextType(private val jsonType: String) {
    PLAIN(jsonType = "plain-text"),
    AUTOCOMPLETE(jsonType = "auto-complete-text-view"),
    UNKNOWN(jsonType = "");

    companion object  {
        fun getByJsonType(jsonType: String): TextType {
            return when (jsonType) {
                PLAIN.jsonType -> PLAIN
                AUTOCOMPLETE.jsonType -> AUTOCOMPLETE
                else -> UNKNOWN
            }
        }
    }
}
