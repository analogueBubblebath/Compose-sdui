package ml.bubblebath.compose_sdui.model

sealed class ErrorMessages(val description: String) {
    data object RequestFailure
        : ErrorMessages("Не удалось выполнить запрос")

    data object UILoadingFailure
        : ErrorMessages("Не удалось загрузить файл верстки с сервера")

    data object LayoutBuildingError :
        ErrorMessages("Произошла ошибка при построении интерфейса из верстки")

    data object UIDeserialization :
        ErrorMessages("Не удалось построить интерфейс по полученному файлу верстки")
}
