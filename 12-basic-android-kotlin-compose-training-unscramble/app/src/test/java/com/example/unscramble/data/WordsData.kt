package com.example.unscramble.data

const val MAX_NO_OF_WORDS = 10
const val SCORE_INCREASE = 20

// List with all the words for the Game
val allWords: Set<String> =
    setOf(
        "at",
        "sea",
        "home",
        "arise",
        "banana",
        "android",
        "birthday",
        "briefcase",
        "motorcycle",
        "cauliflower"
    )

/*
.associateBy(...)
    É uma função de extensão de coleções do Kotlin.
    Ela serve para criar um Map (Mapa de Chave-Valor) onde os elementos da coleção original se tornam os valores do novo mapa.

    1ª argumento: Define como a chave do mapa será calculada. O it representa cada palavra. Portanto, a chave será o tamanho da palavra (Int). - it.length
    2º argumento: Define qual será o valor do mapa. O it aqui representa a própria palavra.
 */
// ou seja: criando um Map chave/valor com a chave sendo a quantidade de caracteres da palavra e o valor sendo a própria palavra
// saida da linha abaixo: {2=at, 3=sea, 4=home, 5=arise, 6=banana, 7=android, 8=birthday, 9=briefcase, 10=motorcycle, 11=cauliflower}
private val wordLengthMap: Map<Int, String> = allWords.associateBy({ it.length }, { it })

// recebe como parâmetro uma palavra embaralhada. Busca no Map acima a palavra desembaralhada.
// A busca é feita usando a chave específica (scrambledWord.length), já que só há uma palavra com cada quantidade de letras
// se não encontrar nada, retorna vazio
internal fun getUnscrambledWord(scrambledWord: String) = wordLengthMap[scrambledWord.length] ?: ""
