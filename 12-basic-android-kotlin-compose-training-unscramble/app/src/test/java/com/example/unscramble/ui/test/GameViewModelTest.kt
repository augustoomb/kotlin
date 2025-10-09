package com.example.unscramble.ui.test;

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNotEquals

// TESTES DE UNIDADE


class GameViewModelTest {
    private val viewModel = GameViewModel()

// ScoreUpdatedAndErrorFlagUnset === PlacarAtualizadoEFlagDeErroDesmarcada
    // CAMINHO DE SUCESSO
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset()  {

        // pegando o estado (dentro contem a palavra embaralhada)
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        // Assert that checkUserGuess() method updates isGuessedWordWrong is updated correctly.
        assertFalse(currentGameUiState.isGuessedWordWrong)
        // Assert that score is updated correctly.
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = 20
    }


    // CAMINHO DE ERRO
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // Given an incorrect word as input
        val incorrectPlayerWord = "and"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value
        // Assert that score is unchanged
        assertEquals(0, currentGameUiState.score)
        // Assert that checkUserGuess() method updates isGuessedWordWrong correctly
        assertTrue(currentGameUiState.isGuessedWordWrong) //palpite errado isGuessedWordWrong deve ser true
    }


    // CASO LIMITE - ver se propriedades iniciais estão no padrão
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        // Assert that current word is scrambled.
        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        // Assert that current word count is set to 1.
        assertTrue(gameUiState.currentWordCount == 1)
        // Assert that initially the score is 0.
        assertTrue(gameUiState.score == 0)
        // Assert that the wrong word guessed is false.
        assertFalse(gameUiState.isGuessedWordWrong)
        // Assert that game is not over.
        assertFalse(gameUiState.isGameOver)
    }

    // CASO LIMITE - testar o estado da IU depois que o usuário descobre todas as palavras
    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            // Assert that after each correct answer, score is updated correctly.
            assertEquals(expectedScore, currentGameUiState.score)
        }
        // Assert that after all questions are answered, the current word count is up-to-date.
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        // Assert that after 10 questions are answered, the game is over.
        assertTrue(currentGameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        val lastWordCount = currentGameUiState.currentWordCount
        viewModel.skipWord()
        currentGameUiState = viewModel.uiState.value
        // Assert that score remains unchanged after word is skipped.
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
        // Assert that word count is increased by 1 after word is skipped.
        assertEquals(lastWordCount + 1, currentGameUiState.currentWordCount)
    }
}




/*
gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset
    Observação: o código acima  usa o formato thingUnderTest_TriggerOfTest_ResultOfTest para dar o nome da função de teste:

        thingUnderTest = gameViewModel   -- (A Coisa Sendo Testada) É o nome da classe, do componente ou da unidade de código que você está testando. Geralmente, é o nome da classe sendo testada (a GameViewModel, nesse caso).
        TriggerOfTest = CorrectWordGuessed   -- (O Gatilho/Condição do Teste) É a ação, o evento ou a condição inicial que você está simulando no teste. É o que o usuário faria ou o que aconteceria no sistema que desencadeia a lógica que você quer verificar. Especifica o cenário em que o teste está sendo executado. Por exemplo: "com a palavra correta adivinhada", "com uma lista vazia", "após um clique do usuário".
        ResultOfTest = ScoreUpdatedAndErrorFlagUnset   -- (O Resultado Esperado). É a verificação ou a mudança de estado que você espera que ocorra após o TriggerOfTest. É a condição final que prova que o código funcionou corretamente. Confirma o comportamento esperado. Deve ser o ponto principal dos seus asserts (as verificações) dentro do corpo da função de teste.
 */

/*
viewModel.uiState.value
    Aviso: essa maneira de recuperar o uiState funciona porque você usou o MutableStateFlow.
    Nas próximas unidades, você vai aprender sobre os usos avançados do StateFlow que criam um
    fluxo de dados e vai precisar reagir para processá-lo. Nesses cenários,
    você vai criar testes de unidade usando diferentes métodos e abordagens.
 */

/*
Ao observar mais de perto a forma como o viewModel é inicializado no teste, você pode perceber que o viewModel é inicializado apenas uma vez, mesmo que todos os testes o usem.

 Você pode ter as seguintes dúvidas:

Isso significa que a mesma instância de viewModel é reutilizada para todos os testes?
Isso causa algum problema? E se o metodo de teste gameViewModel_Initialization_FirstWordLoaded for executado após o metodo de teste gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset, por exemplo? O teste de inicialização vai falhar?
A resposta para essas perguntas é "não". Os métodos de teste são executados de forma isolada para evitar efeitos colaterais inesperados no estado mutável da instância de teste. Por padrão, antes de cada metodo de teste ser executado, o JUnit cria uma nova instância da classe de teste.

Como você tem quatro métodos de teste até o momento na classe GameViewModelTest, a GameViewModelTest é instanciada quatro vezes. Cada instância tem a própria cópia da propriedade viewModel. Portanto, a sequência da execução do teste não importa.

 Observação: esse ciclo de vida da instância de teste "por metodo" é o comportamento padrão desde o JUnit4.
 */