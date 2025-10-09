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




fun main() {
    val trickFunction = ::trick
    // para se referir a uma função como um valor, é necessário usar o operador de referência de função (::)
    // Os parênteses depois de trick não são incluídos porque você não quer chamar a função, mas sim que ela seja armazenada em uma variável.

    val trickFunction2 = trick2
    // se eu declarar a função como uma expressão lambda (trick2), posso chamá-la sem o operador de referência (::)

    // e posso chamar:
    trick()
    trick2()
    trickFunction()
    trickFunction2()
}

fun trick() {
    println("No treats!")
}

val trick2 = {
    println("No treats2!")
}


// ------ tipos

// No Kotlin, tipo de retorno Unit é porque a função não retorna nada.
val treat: () -> Unit = {
    println("Have a treat!")
}


fun trickOrTreat(isTrick: Boolean): () -> Unit {
    if (isTrick) {
        return trick
    } else {
        return treat
    }
}

fun main2() {
    val treatFunction = trickOrTreat(false)
    val trickFunction = trickOrTreat(true)
}



// ----- passando função como parâmetro
fun trickOrTreat(isTrick: Boolean, extraTreat: (Int) -> String): () -> Unit {
    if (isTrick) {
        return trick
    } else {
        println(extraTreat(5))
        return treat
    }
}


//
// expressão lambda atribuida a variável coins
val coins: (Int) -> String = { quantity ->
    "$quantity quarters"
}

// Quando uma função tem um único parâmetro e você não fornece um nome, o Kotlin atribui o nome it implicitamente. Assim, você pode omitir o nome do parâmetro e o símbolo ->, deixando suas expressões lambda mais concisas.
val coinsResumida: (Int) -> String = {
    "$it quarters"
}




// para expressoes simples que são lambdas, eu posso resumir ainda mais se eu for passá-la por parâmetro.
// por exemplo:
fun main() {
    val coins: (Int) -> String = {
        "$it quarters"
    }
    val treatFunction = trickOrTreat(false, { "$it quarters" })
    val trickFunction = trickOrTreat(true, null)
    treatFunction()
    trickFunction()
}
// eu posso resumir assim, já que a lambda é simples, posso passar só a expressao e ignorar a estrutura:
fun main() {

    //removi coins e usei só "$it quarters" como parâmetro abaixo
    val treatFunction = trickOrTreat(false, { "$it quarters" })
    val trickFunction = trickOrTreat(true, null)
    treatFunction()
    trickFunction()
}


// mais uma forma de resumir lambda:
val treatFunction = trickOrTreat(false) { "$it quarters" }





// HOF repeat
    // repeat(times: Int, action: (Int) -> Unit)
        /*
        O parâmetro times é o número de vezes que a ação precisa ocorrer.
        O parâmetro action é uma função que usa um único parâmetro Int
        e retorna um tipo Unit. O parâmetro Int da função action é o número
         de vezes que a ação foi executada até o momento, como um argumento 0
         na primeira iteração ou um 1 na segunda. Você pode usar a função
          repeat() para repetir o código um número específico de vezes,
           semelhante a uma repetição for
         */

        //ex:
        fun main() {
            val treatFunction = trickOrTreat(false) { "$it quarters" }
            val trickFunction = trickOrTreat(true, null)
            repeat(4) {
                treatFunction()
            }
            trickFunction()
        }

        fun main() {
            repeat(4) {
                println(it) //0 1 2 3
            }
        }