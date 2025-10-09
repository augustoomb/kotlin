/*
coleções de elementos únicos, onde cada elemento ocorre apenas
uma vez. Eles são ideais para cenários em que você precisa garantir
 que os dados não contenham duplicatas.
 */

fun criarConjuntoImutavel() {
    val cores = setOf("Vermelho", "Verde", "Azul");

    for(cor in cores) {
        println(cor)
    }

    // ACESSAR ELEMENTOS (CONJUNTO NÃO TEM INDICE IGUAL AS LISTAS)
    val temVermelho = "Vermelho" in cores // RETORNA BOOLEANO
    println(temVermelho)
}

fun criarConjuntoMutavel() {
    val numeros = mutableSetOf(1, 2, 3, 4, 5);

    for(numero in numeros) {
        println(numero)
    }

    // ADICIONAR OU REMOVER ELEMENTOS
    numeros.add(6);
    numeros.remove(3)
}

fun main() {
    criarConjuntoImutavel()
}

/*
 Propriedades e Funções Úteis

Kotlin fornece várias propriedades e funções úteis para trabalhar com conjuntos, incluindo:

    size: Propriedade que retorna o tamanho do conjunto.
    isEmpty(): Função que verifica se o conjunto está vazio.
    isNotEmpty(): Função que verifica se o conjunto não está vazio.

 */