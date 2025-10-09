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

// GENÉRICOS, OBJETOS E EXTENSÕES

//class FillInTheBlankQuestion(
//    val questionText: String,
//    val answer: String,
//    val difficulty: String
//)
//
//class TrueOrFalseQuestion(
//    val questionText: String,
//    val answer: Boolean,
//    val difficulty: String
//)
//
//class NumericQuestion(
//    val questionText: String,
//    val answer: Int,
//    val difficulty: String
//)





enum class Difficulty {
    EASY, MEDIUM, HARD
}

// ao inves das 3 classes acima(comentadas), criou uma com generics. Herança pura não seria o ideal para esse caso
data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)

fun main() {
    val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)

    println(question1.toString())
    // Question@49097b5d - antes de transformar Question em data class
    // Question(questionText=Quoth the raven ___, answer=nevermore, difficulty=MEDIUM)     - depois de transformar em data class
}

/*
Quando uma classe é definida como sendo de dados, os métodos abaixo são implementados.

    equals()
    hashCode(): esse método é encontrado ao trabalhar com alguns tipos de coleção.
    toString()
    componentN() (link em inglês): component1(), component2(), entre outros.
    copy()

 */

/*
Observação: uma classe de dados precisa ter pelo menos um parâmetro no construtor,
e todos os parâmetros do construtor precisam ser marcados com val ou var.
A classe de dados também NÃO pode ser abstract, open, sealed ou inner.
 */















