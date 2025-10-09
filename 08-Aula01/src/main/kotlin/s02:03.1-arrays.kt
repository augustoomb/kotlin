// DECLARANDO ARRAY
fun declarandoArray1() {
    val numeros = arrayOf(1, 2, 3)

    for(i in 0 until numeros.size) { // until inclui 0, exclui numeros.size
        println(numeros[i])
    }
}

/*
Usando a classe Array(): Esta classe permite criar um array
especificando o tamanho e uma função lambda (função anônima)
para inicializar os elementos. Por exemplo:
 */
fun declarandoArray2() {
    val numeros = Array(5) { i -> i * 2 }

    for(i in 0 until numeros.size) {
        println(numeros[i])
    }
}

fun acessandoElementos() {
    val numeros = arrayOf(1, 2, 3)
    val primeiroElemento = numeros[0]
}

fun modificandoElementos() {
    val numeros = arrayOf(1, 2, 3)
    numeros[2] = 10
}

fun iterando1() {
    val numeros = arrayOf(1, 2, 3)

    for(i in 0 until numeros.size){
        println(numeros[i])
    }
}

fun iterandoComHof() {
    val numeros = arrayOf(1, 2, 3)

    numeros.forEach {
        numero -> println(numero)
    }
}

/*
 Propriedades e funções

Kotlin fornece várias propriedades e funções úteis para trabalhar com arrays, incluindo:

    size: Propriedade que retorna o tamanho do array.
    isEmpty() e isNotEmpty(): Funções que verificam se o array está vazio ou não.
    sorted(): Função que retorna um novo array com os elementos classificados.

 */

fun main() {
    iterandoComHof()
}

