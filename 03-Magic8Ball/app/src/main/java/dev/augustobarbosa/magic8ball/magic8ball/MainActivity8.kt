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


enum class Difficulty {
    EASY, MEDIUM, HARD
}

interface ProgressPrintable {
    val progressText: String
    fun printProgressBar()
}


class Quiz : ProgressPrintable {
    val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)


    override val progressText: String
        get() = "${answered} of ${total} answered"


    companion object StudentProgress {
        var total: Int = 10
        var answered: Int = 3
    }

    override fun printProgressBar() {
        repeat(answered) { print("▓") }
        repeat(total - answered) { print("▒") }
        println()
        println(Quiz.progressText)
    }

    fun printQuiz() {
//        println(question1.questionText)
//        println(question1.answer)
//        println(question1.difficulty)
//        println()
//        println(question2.questionText)
//        println(question2.answer)
//        println(question2.difficulty)
//        println()
//        println(question3.questionText)
//        println(question3.answer)
//        println(question3.difficulty)
//        println()

        // MELHORANDO O CÓDIGO ACIMA: - FUNÇÕES DE ESCOPO
        question1.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question2.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question3.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
    }

}


data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)

// Mova a chamada da função printQuiz() para dentro da expressão lambda. Não é mais necessário fazer referência à variável quiz nem usar a notação de ponto.
fun main() {
    val quiz = Quiz()
    quiz.printQuiz()


    Quiz().apply { // Com a função apply(), você não precisa de uma variável para chamar métodos na instância de Quiz.
        printQuiz()
    }

}

/*
O jeito tradicional

val quiz = Quiz()
quiz.printQuiz()

Neste método, você está seguindo uma abordagem mais "passo a passo":

    Cria um objeto Quiz e armazena-o em uma variável chamada quiz.

    Usa a variável quiz para chamar a função printQuiz().

Isso é ótimo quando você precisa da variável quiz para outras operações depois de chamar printQuiz(). Por exemplo, se você quisesse chamar quiz.submitAnswers() em seguida.


------

O jeito com apply

Quiz().apply {
    printQuiz()
}

Aqui, a função de escopo apply entra em ação. Ela é feita para configurar ou inicializar um objeto.

    Cria um objeto Quiz.

    A função apply permite que você execute um bloco de código ({ ... }) nesse objeto.

    Dentro desse bloco, o objeto Quiz se torna o this implícito, então você pode chamar printQuiz() diretamente.

    O apply retorna o objeto original que foi chamado.

A grande vantagem aqui é que você não precisa de uma variável intermediária se for usar o objeto apenas para uma única operação ou para uma série de inicializações. O código fica mais limpo e conciso, especialmente se houver várias chamadas.

Resumindo:
Característica	val quiz = Quiz()	Quiz().apply { ... }
Variável	Cria uma variável (quiz).	Não cria uma variável.
Uso principal	Para quando você precisa referenciar o objeto depois.	Para inicializar ou configurar o objeto e não precisa de uma referência futura.
Estilo	Mais explícito e passo a passo.	Mais conciso e "encadeado".

Para o seu caso simples de apenas chamar printQuiz(), ambos são válidos e produzem o mesmo resultado. A escolha entre eles depende do seu estilo de codificação e se você precisará da referência ao objeto Quiz mais tarde.
 */
