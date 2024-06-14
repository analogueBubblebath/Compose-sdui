package ml.bubblebath.compose_sdui.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ml.bubblebath.compose_sdui.model.ui.JsonUI

class SduiApi(val client: HttpClient) {
    /*
    Не использую встроенные фичи по десериализации, потому что
    ktor воспринимает файловые эндпоинты как ByteReadChannel, если бы мы не читали файлы напрямую,
    то это выглядело бы так
        client.get(path, block).body<JsonUI>()
     */
    suspend inline fun <reified T> get(path: String, block: HttpRequestBuilder.() -> Unit = {}): T {
        val get = client.get(path, block)
        return Json.decodeFromString(get.bodyAsText())
    }
}