package ml.bubblebath.compose_sdui.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import ml.bubblebath.compose_sdui.Constants.BASE_URL
import ml.bubblebath.compose_sdui.api.SduiApi
import ml.bubblebath.compose_sdui.ui.screen.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SduiApi(client = createHttpClient()) }
    viewModel { MainScreenViewModel(api = get()) }
}

private fun createHttpClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }

    install(DefaultRequest) {
        url(BASE_URL)
    }
}