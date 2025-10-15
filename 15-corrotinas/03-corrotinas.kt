/*
una o conteúdo da corrotina iniciada com uma chamada para withContext()
para mudar o CoroutineContext em que a corrotina é executada,
e substitua especificamente o dispatcher.
Comece a usar o Dispatchers.Default, em vez de Dispatchers.Main,
que está sendo usado no momento para o restante do código de corrotina no programa.
 */

fun main() {
    runBlocking {
        launch {
            withContext(Dispatchers.Default) {
                delay(1000)
                println("10 results found.")
            }
        }
        println("Loading...")
    }
}

/*
A troca de agentes é possível porque withContext()
é uma função de suspensão. Ela executa o bloco de código fornecido usando
um novo CoroutineContext. O novo contexto vem do job pai (o bloco launch() externo),
mas substitui o agente usado no contexto pai pelo especificado aqui:
Dispatchers.Default. É assim que podemos executar o trabalho com Dispatchers.Main
e usar Dispatchers.Default.
 */


// --

// Adicione instruções de exibição para conferir em qual linha de execução você está chamando Thread.currentThread().name


import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println("${Thread.currentThread().name} - runBlocking function")
        launch {
            println("${Thread.currentThread().name} - launch function")
            withContext(Dispatchers.Default) {
                println("${Thread.currentThread().name} - withContext function")
                delay(1000)
                println("10 results found.")
            }
            println("${Thread.currentThread().name} - end of launch function")
        }
        println("Loading...")
    }
}

// Execute o programa. A saída vai ser:

/*

    main @coroutine#1 - runBlocking function
    Loading...
    main @coroutine#2 - launch function
    DefaultDispatcher-worker-1 @coroutine#2 - withContext function
    10 results found.
    main @coroutine#2 - end of launch function

 */

/*
Nessa saída, é possível observar que a maior parte do código é executada em corrotinas na linha de execução principal.
No entanto, para a parte do código no bloco withContext(Dispatchers.Default),
que é executada em uma corrotina em uma linha de execução de worker do agente padrão
(que não é a linha de execução principal).
Após o retorno de withContext(), a corrotina retorna para a execução na linha de execução principal
(conforme evidenciado pela instrução de saída: main @coroutine#2 - end of launch function).
Esse exemplo demonstra que é possível alternar o agente modificando o contexto usado para a corrotina.

Se você tiver corrotinas iniciadas na linha de execução principal e quiser mover determinadas operações da
linha de execução principal, use withContext para alternar o agente usado para esse trabalho.
Escolha adequadamente entre os agentes disponíveis: Main, Default e IO, dependendo do tipo de operação.
Em seguida, esse trabalho pode ser atribuído a uma linha de execução
(ou a um grupo de linhas de execução, conhecido como pool de linhas de execução) designada para essa finalidade.
As corrotinas podem suspender a si mesmas, e o agente também influencia como elas são retomadas.

Ao trabalhar com bibliotecas conhecidas, como Room e Retrofit (nesta unidade e na próxima),
talvez não seja necessário alternar explicitamente o agente se o código da biblioteca já processar essa tarefa
usando um agente de corrotina alternativo, como Dispatchers.IO.. Nesses casos,
as funções suspend que essas bibliotecas revelam podem já ser seguras para a linha de execução principal e
podem ser chamadas em uma corrotina em execução na linha de execução principal.
A própria biblioteca vai processar a mudança do agente para um que use as linhas de execução de worker.

Agora você tem uma visão geral de alto nível das partes importantes das corrotinas e do papel que CoroutineScope,
CoroutineContext, CoroutineDispatcher e Jobs desempenham no ciclo de vida e no comportamento de uma corrotina.



FINAL DO CAP:
    O código de corrotina no Kotlin segue o princípio da simultaneidade estruturada.
    Ele é sequencial por padrão. Por isso, é necessário informar explicitamente se você quer simultaneidade
    (por exemplo, usando launch() ou async()). Com a simultaneidade estruturada,
    é possível fazer várias operações simultâneas e colocá-las em uma única operação síncrona,
    em que a simultaneidade é um detalhe de implementação. O único requisito do código de chamada é estar
    em uma função de suspensão ou corrotina. Além disso, a estrutura do código de chamada não precisa
    considerar os detalhes da simultaneidade. Isso facilita a leitura e a compreensão do código assíncrono.

    A simultaneidade estruturada monitora cada uma das corrotinas iniciadas no seu app e garante que elas não sejam perdidas. As corrotinas podem ter uma hierarquia: as tarefas podem iniciar subtarefas que, por sua vez, iniciam subtarefas. Os jobs mantêm a relação pai-filho entre as corrotinas e permitem controlar o ciclo de vida da corrotina.

    Inicialização, conclusão, cancelamento e falha são quatro operações comuns na execução da corrotina. Para facilitar a manutenção de programas simultâneos, a simultaneidade estruturada define princípios que formam a base de como as operações comuns na hierarquia são gerenciadas:

    Inicialização: inicializa uma corrotina em um escopo que tenha um limite definido para a duração dela.
    Conclusão: o job não estará concluído até que os jobs filhos também estejam.
    Cancelamento: essa operação precisa ser propagada para baixo. Quando uma corrotina é cancelada, as corrotinas filhas também precisam ser.
    Falha: essa operação precisa ser propagada para cima. Quando uma corrotina gera uma exceção, o pai cancela todos os filhos e a si mesmo e propaga a exceção até o pai dele. Isso continua até que a falha seja detectada e processada. Isso garante que erros de código sejam relatados corretamente e que nunca sejam perdidos.
 */

/*
Resumo
    As corrotinas permitem criar um código de longa duração executado simultaneamente, sem que seja necessário aprender um novo estilo de programação. A execução de uma corrotina é sequencial por padrão.
    As corrotinas seguem o princípio da simultaneidade estruturada, que ajuda a garantir que o trabalho não seja perdido e seja vinculado a um escopo com um determinado limite de duração. Por padrão, o código é sequencial e coopera com uma repetição de eventos, a menos que você solicite explicitamente a execução simultânea (por exemplo, usando launch() ou async()). Supõe-se que, se você chamar uma função, ela precisa terminar o trabalho completamente (a menos que falhe com uma exceção) no momento em que for retomada, independente de quantas corrotinas ela possa ter usado nos detalhes de implementação.
    O modificador suspend é usado para marcar uma função cuja execução pode ser suspensa e retomada em um momento futuro.
    Uma função suspend só pode ser chamada em outra função de suspensão ou em uma corrotina.
    Você pode iniciar uma nova corrotina usando as funções de extensão launch() ou async() no CoroutineScope.
    Os jobs desempenham um papel importante para garantir a simultaneidade estruturada, gerenciando o ciclo de vida das corrotinas e mantendo a relação pai-filho.
    Um CoroutineScope controla o ciclo de vida das corrotinas com o job e aplica o cancelamento e outras regras aos filhos e aos filhos deles recursivamente.
    Um CoroutineContext define o comportamento de uma corrotina e pode incluir referências a um job e a um agente de corrotina.
    As corrotinas usam um CoroutineDispatcher para determinar as linhas de execução que serão usadas.
 */