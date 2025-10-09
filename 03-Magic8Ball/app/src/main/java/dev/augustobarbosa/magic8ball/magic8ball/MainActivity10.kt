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

// -------HOFs com coleções-------

class Cookie(
    val name: String,
    val softBaked: Boolean,
    val hasFilling: Boolean,
    val price: Double,
)

val cookies = listOf(
    Cookie(
        name = "Chocolate Chip",
        softBaked = false,
        hasFilling = false,
        price = 1.69
    ),
    Cookie(
        name = "Banana Walnut",
        softBaked = true,
        hasFilling = false,
        price = 1.49
    ),
    Cookie(
        name = "Vanilla Creme",
        softBaked = false,
        hasFilling = true,
        price = 1.59
    ),
    Cookie(
        name = "Chocolate Peanut Butter",
        softBaked = false,
        hasFilling = true,
        price = 1.49
    ),
    Cookie(
        name = "Snickerdoodle",
        softBaked = true,
        hasFilling = false,
        price = 1.39
    ),
    Cookie(
        name = "Blueberry Tart",
        softBaked = true,
        hasFilling = true,
        price = 1.79
    ),
    Cookie(
        name = "Sugar and Sprinkles",
        softBaked = false,
        hasFilling = false,
        price = 1.39
    )
)

fun main() {
    // ForEach() - mostrar itens
    cookies.forEach {
        println("Menu item ${it.name}")
    }

    //--------

    // FUNÇÃO map()
    /*
    A função map() permite transformar uma coleção em uma nova com o mesmo número de elementos. Por exemplo, map() poderia transformar uma List<Cookie> em uma List<String> contendo apenas o name do biscoito, desde que você informe à função map() como criar uma String para cada item de Cookie
     */

    val fullMenu = cookies.map {
        "${it.name} - $${it.price}" // Observação: um segundo $ é usado antes da expressão. O primeiro é tratado como o caractere do cifrão ($), porque não é seguido por um nome de variável ou expressão lambda.
    }
    println("Full menu:")
    fullMenu.forEach {
        println(it)
    }


    //--------

    // FUNÇÃO filter()
    /*
    A função filter() permite criar um subconjunto de itens de uma coleção. Por exemplo, você poderia usar filter() em uma lista de números para criar uma nova lista contendo apenas números divisíveis por 2.
     filtrar uma List<Cookie> resulta em outra List<Cookie>  - sempre o mesmo tipo
     */
    val softBakedMenu = cookies.filter {
        it.softBaked //verificar se a propriedade softBaked do biscoito é igual a true. Como softBaked é um Boolean, o corpo da lambda só precisa conter it.softBaked.
    }




    //--------

    // FUNÇÃO groupBy()
    /*
    pode ser usada para transformar uma lista em um mapa. Cada valor de retorno exclusivo dessa função se transforma em uma chave no mapa gerado. Os valores de cada chave correspondem a todos os itens da coleção que produziu o valor de retorno exclusivo.
     */
    /*
    no caso do exemplo abaixo, o grupBy transformou a lista de cookies em um mapa (chave:valor), onde:
        -são criadas 2 chaves: true e false (graças ao resultado de { it.softBaked } )
        -cada uma delas contem uma lista de cookies (se softBaked for true, vai para uma das listas, se for false, vai pra outra)
        -Em resumo, temos um tipo: Map<Boolean, List<Cookie>>     - ou seja. A chave é um booleano e o valor é uma lista de cookies
     */
    val groupedMenu = cookies.groupBy { it.softBaked }
    groupedMenu[true]?.forEach {
        println("${it.name}")
    }


    // mostrando as listas separadamente
    val softBakedMenu = groupedMenu[true] ?: listOf()
    val crunchyMenu = groupedMenu[false] ?: listOf()

    println("Soft cookies:")
    softBakedMenu.forEach {
        println("${it.name} - $${it.price}")
    }
    println("Crunchy cookies:")
    crunchyMenu.forEach {
        println("${it.name} - $${it.price}")
    }




    //--------

    // FUNÇÃO fold()
    /*
    A função fold() é usada para gerar um valor único de uma coleção. Normalmente, esse recurso é usado para calcular o preço total, somar todos os elementos de uma lista ou encontrar um valor médio.
     */

    // 0.0 passado em "fold" é valor inicial - tipo inferido, obviamente é o Double
    // 1º param é o acumulador. Chamei de "total"
    // 2º param é a função. ou seja, para cada cookie, pego o cookie.price e somo como o total (acumulador)
    val totalPrice = cookies.fold(0.0) {total, cookie ->
        total + cookie.price
    }

    println("Total price: $${totalPrice}")

    /*
    Observação: às vezes, a função fold() é escrita como reduce().
    Em Kotlin, fold() funciona da mesma maneira que reduce() em JavaScript, Swift, Python, entre outros.
    Observe que o Kotlin também tem uma função reduce() (link em inglês), em que o acumulador começa
     com o primeiro elemento na coleção, em vez de um valor inicial transmitido como um argumento.
     */



    //--------

    // FUNÇÃO sortedBy()
    /*
    Ao começar a estudar sobre coleções, você aprendeu que a função sort() podia ser usada para classificar elementos. No entanto, isso não funciona em uma coleção de objetos Cookie. Como a classe Cookie tem várias propriedades, o Kotlin não consegue saber se você quer classificar name, price ou alguma outra propriedade.
     */
    /*
    As coleções em Kotlin têm uma função sortedBy() para esses casos. sortedBy() permite especificar uma lambda que retorna a propriedade que você quer classificar. Por exemplo, se você quiser classificar os itens por price, a lambda vai retornar it.price. Desde que o tipo de dados do valor esteja em uma ordem de classificação lógica, ou seja, com strings em ordem alfabética e valores numéricos em ordem crescente, os dados vão ser classificados como um conjunto desse tipo.
     */

    val alphabeticalMenu = cookies.sortedBy {
        it.name
    }
    println("Alphabetical menu:")
    alphabeticalMenu.forEach {
        println(it.name)
    }
}




































