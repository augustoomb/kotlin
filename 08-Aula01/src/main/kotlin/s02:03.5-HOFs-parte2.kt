// Funções de Alta Ordem para Listas

// MAP - Aplica uma função a cada elemento da lista e retorna uma nova lista com os resultados.
fun usandoMap() {
    val nums = listOf(1,2,3,4,5)
    val aoQuadrado = nums.map{ it * it } // it é palavra reservada, não escolha de nome de var
    val aoQuadradoIgualAoAnteriorMasFacil = nums.map{numero -> numero * numero}

    for(item in aoQuadrado) {
        println(item)
    }

    for (item in aoQuadradoIgualAoAnteriorMasFacil) {
        println(item)
    }
}

// FILTER - Retorna uma nova lista contendo apenas os elementos que atendem a um critério específico
fun usandoFilter() {
    var numeros = listOf(1,2,3,4,5);

    val pares = numeros.filter { it % 2 == 0 }
    val paresMaisSimples = numeros.filter { numero -> numero % 2 == 0 }

    for (par in pares) {
        println(par)
    }

    for (par in paresMaisSimples) {
        println(par)
    }
}

// REDUCE: COMBINA OS ELEMENTOS DA LISTA EM UNICO VALOR, USANDO UMA OPERAÇÃO ESPECÍFICA
fun usandoReduce() {
    val numeros = listOf(1,2,3,4,5);

    val somaTotal = numeros.reduce { acc, numero -> acc + numero }

    println(somaTotal)
}

// forEach - ITERA SOBRE OS ELEMENTOS DA LISTA E APLICA UMA FUNÇÃO DA CADA UM DELES
fun usandoForEach() {
    val numeros = listOf(1,2,3,4,5);

    numeros.forEach { numero -> println(numero) }
}

// ALL - VERIFICA SE TODOS OSO ELEMENTOS DA LISTA ATENDEM A UM CRITÉRIO ESPECÍFICO
fun usandoAll() {
    val numeros = listOf(1,2,3,4,5);

    val todosPares = numeros.all{ it % 2 == 0 }
    val todosParesMaisSimples = numeros.all{ numero -> numero % 2 == 0 }

    println(todosPares)
    println(todosParesMaisSimples)
}

fun main() {
    usandoAll();
}

