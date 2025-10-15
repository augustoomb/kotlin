import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println("Weather forecast")  
        println(getWeatherReport()) // Toda a lógica dentro de getWeatherReport() precisa ser concluída antes que a execução do runBlocking possa prosseguir para a próxima linha.              
        println("Have a good day!")
    }
}

suspend fun getWeatherReport() = coroutineScope { // coroutineScope() só vai retornar depois que todo o trabalho for concluído, incluindo as corrotinas iniciadas
    val forecast = async { getForecast() }
    
    val temperature = async {
        try {
            getTemperature()
        } catch (e: AssertionError) {
            println("Caught exception $e")
            "{ No temperature found }"
        }
    }
    
    "${forecast.await()} ${temperature.await()}"
}

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(500)
    throw AssertionError("Temperature is invalid")
    return "30\u00b0C"
}
