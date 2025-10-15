import kotlinx.coroutines.*
// job: controla o ciclo de vida da corrotina

/*
Neste codelab, você usou runBlocking(),
que fornece um CoroutineScope para o programa.

Você também aprendeu a usar coroutineScope { }
para criar um novo escopo na função getWeatherReport().
 */

/*
As corrotinas geralmente são iniciadas em um CoroutineScope. (
Isso garante que não haja corrotinas não gerenciadas e que sejam perdidas,
o que pode desperdiçar recursos.
CoroutineScope tem ciclo de vida
 */

fun main() {
    runBlocking {
        println("Weather forecast")
        println(getWeatherReport())
        println("Have a good day!")
    }
}

// Chame launch() ou async() no escopo (coroutineScope) para criar uma nova corrotina dentro dele.
suspend fun getWeatherReport() = coroutineScope {
    val forecast = async { getForecast() } // VER TBM SOBRE O USO DE lauch() ao invés do async
    val temperature = async { getTemperature() }
    
    delay(200)
    temperature.cancel() // O que você aprendeu aqui é que uma corrotina pode ser cancelada, mas não afeta outras corrotinas no mesmo escopo, e a corrotina mãe não será cancelada.

    "${forecast.await()}"
}

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(1000)
    return "30\u00b0C"
}


/*
CoroutineContext

O CoroutineContext fornece informações sobre o contexto em que a corrotina será executada.
O CoroutineContext é essencialmente um mapa que armazena elementos em que cada elemento tem uma chave exclusiva.
Esses campos não são obrigatórios, mas confira alguns exemplos do que pode estar contido em um contexto:

    name: nome da corrotina para identificá-la exclusivamente.
    job: controla o ciclo de vida da corrotina.
    dispatcher: agente que envia o trabalho para a linha de execução adequada.
    exception handler: lida com as exceções geradas pelo código executado na corrotina.

Observação: esses são os valores padrão do CoroutineContext, que serão usados se você não fornecer valores para eles:

    "coroutine" para o nome da corrotina.
    Nenhum job pai.
    Dispatchers.Default para o agente de corrotina.
    Nenhum gerenciador de exceções.


Cada um dos elementos de um contexto pode ser anexado ao operador +.
Por exemplo, um CoroutineContext pode ser definido desta maneira:

    Job() + Dispatchers.Main + exceptionHandler

Como um nome não é fornecido, o nome de corrotina padrão é usado.

Em uma corrotina, se você iniciar uma nova, a filha vai herdar o CoroutineContext da corrotina mãe,
mas vai substituir o job especificamente pela corrotina recém-criada.
Também é possível substituir qualquer elemento herdado do contexto pai
transmitindo argumentos para as funções launch() ou async() das partes do contexto que você quer que sejam diferentes.


Há alguns agentes integrados fornecidos pelo Kotlin:

-> Dispatchers.Main:: use esse agente para executar uma corrotina na linha de execução principal do Android. Esse agente é usado principalmente para processar atualizações e interações da IU e realizar trabalhos rápidos.
-> Dispatchers.IO:: esse agente é otimizado para executar E/S de disco ou rede fora da linha de execução principal. Por exemplo, ler ou gravar arquivos e executar qualquer operação de rede.
-> Dispatchers.Default:: esse agente padrão é usado ao chamar launch() e async() quando nenhum agente é especificado no contexto. Use esse agente para realizar trabalhos com uso intenso de computação fora da linha de execução principal. Por exemplo, processamento de um arquivo de imagem de bitmap.
 */