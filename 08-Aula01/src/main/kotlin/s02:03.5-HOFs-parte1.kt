// USO BÁSICO

//fun main() {
//    println(soma(5, 7))
//
//    val resultado = operacaoBinaria(5, 7, ::soma)// Passando a função "soma" como argumento
//}
//
//fun soma(a:Int, b:Int):Int {
//    return a+b;
//}

// USO AVANÇADO COM HOF

//Você também pode retornar funções de outras funções em Kotlin. Aqui está um exemplo:
fun main() {

    // HOF - A função multiplicaPor é uma Higher-Order Function porque ela retorna outra função.
    fun multiplicaPor(valor: Int): (Int) -> Int {
        return { numero -> numero * valor }
    }

    val triplica = multiplicaPor(3) // Atribuindo a função retornada a uma variável
    val resultado = triplica(5) // Chamando a função para obter o resultado

    println(triplica)
    println(resultado)
}

/*

Vamos analisar esse código de Kotlin, que é um ótimo exemplo de como usar Higher-Order Functions (Funções de Ordem Superior) e closures.

Função multiplicaPor

Kotlin

fun multiplicaPor(valor: Int): (Int) -> Int {
    return { numero -> numero * valor }
}

Essa é a parte mais importante. A função multiplicaPor é uma Higher-Order Function porque ela retorna outra função.

    fun multiplicaPor(valor: Int): Recebe um único parâmetro, valor, do tipo Int.

    :(Int) -> Int: A parte após os dois pontos é o tipo de retorno da função. Isso não é um Int ou um String, é o tipo de uma função!

        (Int): Indica que a função retornada receberá um parâmetro do tipo Int.

        -> Int: Indica que a função retornada produzirá um resultado do tipo Int.

    return { numero -> numero * valor }: Aqui, a função multiplicaPor retorna uma função anônima (também conhecida como lambda).

        { numero -> ... }: É a sintaxe de uma lambda em Kotlin. O nome numero é o parâmetro da lambda.

        numero * valor: O corpo da lambda. O mais interessante aqui é que ela acessa a variável valor que foi passada para a função "pai" (multiplicaPor). Isso é chamado de closure. A lambda "fecha" (captura) a variável valor do escopo em que foi criada e a mantém mesmo depois que a função multiplicaPor termina sua execução.

Declaração de triplica

Kotlin

val triplica = multiplicaPor(3)

    A função multiplicaPor é chamada com o argumento 3.

    Ela executa, e a lambda { numero -> numero * 3 } é criada e retornada.

    Essa função anônima é então atribuída à variável triplica.

A variável triplica agora não é um número, é uma função! Especificamente, uma função que espera um Int e retorna outro Int (conforme o tipo de retorno que vimos acima). Essa função "memorizou" o valor 3 do seu escopo de origem.

Chamada e Impressão

Kotlin

val resultado = triplica(5)
println(triplica)
println(resultado)

    val resultado = triplica(5): Aqui, você chama a função que está armazenada na variável triplica, passando 5 como argumento. A função executa a operação 5 * 3 e o resultado, 15, é atribuído à variável resultado.

    println(triplica): Quando você imprime a variável triplica, o Kotlin mostra a representação da função em si, que geralmente é algo como (kotlin.Int) -> kotlin.Int. Isso confirma que triplica é uma referência a uma função.

    println(resultado): Imprime o valor da variável resultado, que é 15.

Em resumo, a função multiplicaPor age como uma fábrica de funções que personaliza o comportamento da função de multiplicação. Você pode criar novas funções como dobra, quadruplica, etc., apenas passando um valor diferente para multiplicaPor.

 */