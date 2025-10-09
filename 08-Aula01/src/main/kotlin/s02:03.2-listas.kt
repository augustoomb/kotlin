fun listaImutavel() {
    val numeros = listOf<Int>(1,2,3,4)

    for(i in 0 until numeros.size) {
        println(numeros[i])
    }
}

fun listaMutavel() {
    val frutas = mutableListOf<String>("Maça", "Banana", "Uva");

    for(i in 0 until frutas.size) {
        println(frutas[i])
    }

    frutas.add("Morango")
    frutas.remove("Banana")

    // OUTRA FORMA DE USAR "FOR"
    for(fruta in frutas) {
        println(fruta)
    }
}

fun main() {
    listaMutavel();
}

/*
 Propriedades e Funções Úteis

Kotlin fornece várias propriedades e funções úteis para trabalhar com listas, incluindo:

    size: Propriedade que retorna o tamanho da lista.
    isEmpty(): Função que verifica se a lista está vazia.
    isNotEmpty(): Função que verifica se a lista não está vazia.
    sorted(): Função que retorna uma nova lista com os elementos classificados.

 */
