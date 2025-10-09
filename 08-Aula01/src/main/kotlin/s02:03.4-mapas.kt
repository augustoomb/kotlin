/*
Os mapas são estruturas de dados fundamentais em Kotlin que
permitem armazenar pares chave-valor,
onde cada chave está associada a um valor correspondente.
 */

fun mapaImutavel() {
    val estudanteNota = mapOf("Joao" to 8, "Maria" to 9, "Pedro" to 7)

    for((estudante, nota) in estudanteNota) {
        println("Estudante $estudante obteve nota $nota")
    }

    // ACESSANDO VALOR
    val notaJoao = estudanteNota["Joao"]
    println(notaJoao) // 8
}

fun mapaMutavel() {
    val dicionario = mutableMapOf("Maça" to "Frutas", "Carro" to "Veiculo")

    for((palavra, significado) in dicionario) {
        println("Palavra $palavra significa $significado")
    }

    // ADICIONAR, ATUALIZAR E REMOVER
    dicionario["Cachorro"] = "Animal" //ADICIONA NOVO PAR CHAVE VALOR
    dicionario["Carro"] = "Automovel" //ATUALIZA O VALOR ASSOCIADO A CARRO
    dicionario.remove("Maça") //REMOVE O PAR CHAVE-VALOR COM A CHAVE "MAÇA"
}

fun main() {
    mapaImutavel()
}

/*
 Propriedades e funções uteis

Kotlin fornece várias propriedades e funções úteis para trabalhar com mapas, incluindo:

    size: Propriedade que retorna o número de pares chave-valor no mapa.
    isEmpty(): Função que verifica se o mapa está vazio.
    isNotEmpty(): Função que verifica se o mapa não está vazio.

 */