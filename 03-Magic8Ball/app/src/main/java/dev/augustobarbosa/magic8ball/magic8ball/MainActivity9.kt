package dev.augustobarbosa.magic8ball.magic8ball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.augustobarbosa.magic8ball.magic8ball.ui.theme.Magic8BallTheme
import kotlin.random.Random
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// -------COLEÇÕES-------

// MATRIZ
/*
Uma matriz tem um tamanho fixo. Isso significa que não é possível adicionar elementos a uma matriz que excedam esse tamanho.
 */


// Como o Kotlin usa a inferência de tipo, você pode omitir o nome do tipo ao chamar arrayOf()
val rockPlanets = arrayOf<String>("Mercury", "Venus", "Earth", "Mars")
val gasPlanets = arrayOf("Jupiter", "Saturn", "Uranus", "Neptune")

// O resultado é uma nova matriz contendo todos os elementos das matrizes rockPlanets e gasPlanets
val solarSystem = rockPlanets + gasPlanets


fun main() {
    // mostrando elemento da matriz
    println(solarSystem[5])

    // mudança de valores na matriz
    solarSystem[3] = "Little Earth"

    // GERA EXCEÇÃO: A matriz já tem oito elementos, então, como esperado, não é possível simplesmente adicionar um nono.
    solarSystem[8] = "Pluto"

    // Se você quiser aumentar uma matriz, precisa criar uma nova. Defina uma nova variável com o nome newSolarSystem, conforme mostrado. Essa matriz pode armazenar nove elementos em vez de oito.
    val newSolarSystem = arrayOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto")
}

// --------------

// LISTAS
/*
Uma lista é uma coleção redimensionável ordenada, geralmente implementada como uma matriz redimensionável. Quando a matriz está totalmente preenchida e você tenta inserir um novo elemento, ela é copiada para uma nova matriz maior.
 */

/*
O que List e MutableList fazem?

    A List é uma interface que define propriedades e métodos relacionados a uma coleção ordenada de itens somente leitura.
    A MutableList amplia a interface List definindo métodos para modificar uma lista, como a adição e remoção de elementos.

 */
fun main() {
    // CRIANDO LISTA
    val solarSystem = listOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")

    // MOSTRANDO O TAMANHO
    println(solarSystem.size)

    // MOSTRANDO PLANETA NO INDICE 2 usando a sintaxe de subscrito.:
    println(solarSystem[2])

    // MOSTRANDO PLANETA NO INDICE 2 chamando get():
    println(solarSystem.get(2))

    // O método indexOf() pesquisa um determinado elemento na lista, transmitido como um argumento, e retorna o índice da primeira ocorrência desse elemento. Se o elemento não aparecer na lista, ele vai retornar -1
    println(solarSystem.indexOf("Earth")) // 2
    println(solarSystem.indexOf("Pluto")) // -1   POIS NÃO EXISTE

    // PERCORRENDO LISTA USANDO FOR
    for (planet in solarSystem) {
        println(planet)
    }

    // Adicionar elementos a uma lista:
    // A capacidade de adicionar, remover e atualizar elementos de uma coleção é exclusiva das classes que implementam a interface MutableList. Se você estivesse monitorando planetas recém-descobertos, provavelmente gostaria de poder adicionar elementos a uma lista com frequência. É necessário chamar especificamente a função mutableListOf(), em vez de listOf(), ao criar uma lista em que você quer adicionar e remover elementos.

    // mutable list
    val solarSystemMutable = mutableListOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")

    // add (opção sem especificar o indice)
    solarSystemMutable.add("Pluto")

    // add (opção especificando o indice)
    //Insira "Theia" no índice 3, entre "Earth" e "Mars"
    solarSystemMutable.add(3, "Theia")

    // Atualize o valor no índice 3 para "Future Moon"
    solarSystemMutable[3] = "Future Moon"

    // Isso vai remover o indice 9("Pluto") da lista
    solarSystemMutable.removeAt(9)

    // Isso deve fazer uma pesquisa na lista e, se um elemento correspondente for encontrado, ele vai ser removido.
    solarSystemMutable.remove("Future Moon")


    // A List fornece o método contains(), que retorna um Boolean caso um elemento exista em uma lista. Mostre o resultado da chamada de contains() para "Pluto"
    println(solarSystem.contains("Pluto"))

    //Para ter uma sintaxe ainda mais concisa, use o operador in. É possível verificar se um item está em uma lista usando o elemento, o operador in e a coleção. Use o operador in para verificar se solarSystem contém "Future Moon"
    println("Future Moon" in solarSystem)
}


// --------------

// CONJUNTOS (SET)
/*
Um conjunto é uma coleção que não tem uma ordem específica e não permite valores duplicados.
um conjunto usa códigos hash como índices de matriz. Como pode haver cerca de 4 bilhões de códigos hash diferentes, um Set não é apenas uma matriz gigante. Em vez disso, considere um Set como uma matriz de listas.
 */

/*
    Em comparação com listas, pesquisar um elemento específico em um conjunto é rápido, especialmente para coleções grandes. O método indexOf() em uma List exige a verificação de cada elemento desde o início até uma correspondência ser encontrada. Já para conjuntos, em média, a mesma quantidade de tempo é necessária para verificar se um elemento está na coleção, seja ele o primeiro ou o milionésimo.
    Conjuntos costumam usar mais memória do que listas para a mesma quantidade de dados, já que mais índices de matriz costumam ser necessários do que os dados do conjunto.
 */

/*
Assim como há a List e a MutableList, há um Set e um MutableSet. O MutableSet implementa o Set, então qualquer classe que implemente MutableSet precisa implementar ambos.
 */

fun main() {
    val solarSystem = mutableSetOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")

    // MOSTRAR TAMANHO DO SET(CONJUNTO)
    println(solarSystem.size)

    // ADICIONANDO ELEMENTO
    solarSystem.add("Pluto")

    // VERIFICA SE ELEMENTO ESTÁ CONTIDO
    println(solarSystem.contains("Pluto"))
    //OU
    println("Pluto" in solarSystem)

    // SE EU TENTAR ADICIONAR UM ELEMENTO QUE JÁ EXISTE:
    solarSystem.add("Pluto") // SE EU VERIFICAR O CONJUNTO NOVAMENTE, VEREI QUE ELE NÃO FOI ADICIONADO

    // REMOVER ELEMENTO
    solarSystem.remove("Pluto")

    // Observação: lembre-se de que os conjuntos são uma coleção não ordenada. Não há como remover um valor de um conjunto pelo índice dele, já que os conjuntos não têm índices.

}


// --------------

// MAPS
/*
Um Map é uma coleção de chaves e valores. Nele, as chaves exclusivas são associadas a outros valores. Uma chave e o valor dela costumam ser conhecidos como key-value pair (par de chave-valor).
 */

// OBS: planetas vs nº de luas
fun main() {
    val solarSystem = mutableMapOf(
        "Mercury" to 0,
        "Venus" to 0,
        "Earth" to 1,
        "Mars" to 2,
        "Jupiter" to 79,
        "Saturn" to 82,
        "Uranus" to 27,
        "Neptune" to 14
    )

    // tamanho:
    println(solarSystem.size)

    // adicionando
    solarSystem["Pluto"] = 5

    // Mostre o número de luas para a chave "Pluto"
    println(solarSystem["Pluto"])

    // Mostre o número de luas para "Theia"
    println(solarSystem.get("Theia")) // Theia não está no mapa, chamar get() retorna null

    // O método remove() remove o par de chave-valor com a chave especificada. Ele também vai retornar o valor removido, ou null, se a chave especificada não estiver no mapa.
    solarSystem.remove("Pluto")

    // também é possível modificar um valor de uma chave já existente. Use essa sintaxe para atualizar as luas de Júpiter para 78
    solarSystem["Jupiter"] = 78
}










