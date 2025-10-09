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


// USAR UM OBJETO SINGLETON
/*
Para comunicar claramente no código que um objeto precisa apenas de uma instância, defina-o como um singleton. Um singleton é uma classe que pode ter apenas uma instância. O Kotlin oferece uma estrutura especial, conhecida como objeto, que pode ser usada para criar uma classe singleton.
 A sintaxe de um objeto é semelhante à de uma classe. Basta usar a palavra-chave object, em vez da palavra-chave class
 Um objeto singleton não pode ter um construtor, porque ele não pode criar instâncias diretamente. Em vez disso, todas as propriedades são definidas dentro das chaves e recebem um valor inicial.
 */

// ENUM
enum class Difficulty {
    EASY, MEDIUM, HARD
}

class Quiz {
    val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)

    companion object StudentProgress {
        var total: Int = 10
        var answered: Int = 3
    }
}

// EXTENSÃO

// essa linha de código cria uma forma mais limpa e organizada de acessar a informação de progresso do quiz, agindo como um atalho inteligente para a string de formatação que você deseja.
val Quiz.StudentProgress.progressText: String // Kotlin: "Adicione uma nova propriedade, chamada progressText, ao objeto Quiz.StudentProgress."  SE CHAMA "EXTENSÃO"
    get() = "${answered} of ${total} answered"


fun Quiz.StudentProgress.printProgressBar() { // função de extensão. extendendo StudentProgress
    repeat(Quiz.answered) { print("▓") }
    repeat(Quiz.total - Quiz.answered) { print("▒") }
    println()
    println(Quiz.progressText)
}

// GENERICS
data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)

fun main() {
//    println(Quiz.progressText) // 3 of 10 answered.

    Quiz.printProgressBar()
}

/*
Em Kotlin, um companion object é como uma área de trabalho compartilhada para uma classe. Ele permite que você declare propriedades e funções que pertencem à classe em si, em vez de a instâncias individuais (objetos) dessa classe. Pense nele como um lugar para armazenar coisas que são as mesmas, não importa quantos objetos da classe você crie.

A analogia do livro de receitas

Imagine que a classe Quiz é uma receita de bolo 🍰. Você pode fazer vários bolos (instâncias da classe), e cada bolo terá seus próprios ingredientes (as propriedades da instância, como question1, question2, etc.).

Agora, o companion object é como uma anotação na capa do seu livro de receitas que diz: "Este livro tem 10 receitas no total, e você já experimentou 3 delas". Essa informação (total e answered) é relevante para o livro inteiro (a classe Quiz), e não para um bolo específico que você fez. Você não precisa fazer um bolo para saber quantas receitas o livro contém. É uma informação que existe independentemente de você criar um objeto Quiz ou não.

Por que usar um companion object?

    Acesso sem instâncias: Você pode acessar as propriedades e funções do companion object diretamente usando o nome da classe, como visto no seu exemplo: Quiz.answered. Isso é útil para utilitários, constantes ou estado compartilhado.

    Membros estáticos: Ele serve como o equivalente em Kotlin aos membros estáticos de outras linguagens, como Java. Eles são carregados na memória quando a classe é carregada, e há apenas uma cópia para todos os objetos da classe.

    Encapsulamento: Permite agrupar logicamente dados ou funções que estão intimamente ligados à classe, mas que não dependem do estado de uma instância, mantendo a coesão do código.

No seu código, StudentProgress é o nome do seu companion object. Dentro dele, total e answered são propriedades que acompanham o progresso geral do quiz, não o progresso de um objeto Quiz específico. Isso permite que você acesse e exiba esse progresso globalmente, de qualquer lugar no seu código, sem a necessidade de criar uma nova instância de Quiz.
 */



/*
Quando você declara:
Kotlin

val Quiz.StudentProgress.progressText: String

Você está dizendo ao Kotlin: "Adicione uma nova propriedade, chamada progressText, ao objeto Quiz.StudentProgress."

O nome progressText foi "inventado" por quem escreveu o código, mas a partir do momento que ele é declarado como uma propriedade de extensão, o Kotlin o trata como se ele fosse um membro original daquele companion object. É por isso que você pode acessá-lo usando a notação de ponto (.), da mesma forma que acessa as propriedades total e answered.

Pense assim: a property de extensão é uma forma de "adicionar" um nome e uma funcionalidade a uma classe (ou a um companion object), mesmo que você não tenha acesso ao código-fonte original dela. O Kotlin, então, cuida de fazer com que esse novo nome se comporte exatamente como uma propriedade comum.
 */












