package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.unscramble.data.allWords
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE

class GameViewModel : ViewModel() {
    // Game UI state - atributos RODAM ANTES DO INIT
    /*
        MutableStateFlow: Rastrear o Estado Completo e complexo da UI
        mutableStateOf: Rastrear dados simples e locais do ViewModel que a UI precisa (geralmente entrada do usuário).
    */
    private val _uiState = MutableStateFlow(GameUiState()) // StateFlow é um fluxo observável detentor de dados que emite as atualizações de estado novas e atuais. No Android, StateFlow funciona bem com classes que precisam manter um estado imutável observável.
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow() // (expondo o estado criado acima. O asStateFlow() transforma esse fluxo de estado mutável em um fluxo de estado somente leitura.

    // Cria uma variável para armazenar o texto que o usuário está digitando no campo de entrada
    // O by (propriedade delegada) permite que essa variável seja lida diretamente no Compose, e o mais importante: qualquer Composable que a use será automaticamente recomposto (redesenhado) quando o valor de userGuess for alterado.
    var userGuess by mutableStateOf("") //*
        private set // pode ser lido publicamente, mas só pode ser modificado internamente

    private lateinit var currentWord: String // palavra correta e não embaralhada que o jogador precisa adivinhar no momento. É privada, pois a UI só precisa da versão embaralhada.
    private var usedWords: MutableSet<String> = mutableSetOf() // Um conjunto (Set) de palavras já utilizadas. Um Set é ideal para isso porque garante que não haverá palavras duplicadas

    // É a porta de entrada para a UI comunicar a mudança no campo de texto para o ViewModel
    // Recebe o novo texto digitado (guessedWord) e o atribui à variável de estado observável (userGuess)
    // Assim que esta função é chamada, o userGuess é atualizado, o que imediatamente recompõe o GameScreen (e o OutlinedTextField que está usando essa variável), mostrando o caractere recém-digitado na tela.
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    fun checkUserGuess() { //* checar se usuário acertou!
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE) // ** se usuário acertou, incrementar valor do score (pontos). Pego o valor do score no estado e jogo nessa variável para salvar, pois mais tarde vou atualizar o score do estado. O valor de SCORE_INCREASE está definido no arquivo wordsData
            updateGameState(updatedScore) // ** chama a função passando o valor de updatedScore (ou seja, somei o que já tinha lá e incrementei +20)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true) // isGuessedWordWrong lá no GameUiState é imutável (val); por isso o copy é necessário
            }
        }
        // Reset user guess
        updateUserGuess("")
    }

    // **
    private fun updateGameState(updatedScore: Int) { // 20
        if (usedWords.size == MAX_NO_OF_WORDS) { // *** SE CHEGUEI NO MÁXIMO DE RODADAS
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true // ***
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy( // no copy, só troca o que eu editar. O restante é igual
                    isGuessedWordWrong = false, // palpiteErrado volta para falso (default)
                    currentScrambledWord = pickRandomWordAndShuffle(), // gera uma nova palavra aleatória, já que o usuário acertou a ultima
                    score = updatedScore, // atualiza os pontos com o parâmetro que chegou
                    currentWordCount = currentState.currentWordCount.inc(), // ** incrementa em 1 o contador de palavras (É a quantidade de palavras exibidas, não os pontos)
                )
            }
        }
    }

    // **
    fun skipWord() {
        updateGameState(_uiState.value.score) // atualiza o estado do jogo sem incrementar pontos
        // Reset user guess
        updateUserGuess("") // palpite do usuário é setado como vazio
    }

    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.random() // pega palavra aleatório do conjunto em WordsData
        if (usedWords.contains(currentWord)) { // se palavra selecionada já existe...
            return pickRandomWordAndShuffle() // ...recursão: o metodo se inicia novamente
        } else { // ... se ainda não foi usada
            usedWords.add(currentWord) // ... é adicionada no conjunto de palavras usadas
            return shuffleCurrentWord(currentWord) // chama outro metodo passando palavra sorteada
        }
    }

    private fun shuffleCurrentWord(word: String): String { //... recebe palavra sorteada
        val tempWord = word.toCharArray() // converte palavra em array de caracteres
        tempWord.shuffle() // metodo shuffle() do Kotlin, que reorganiza aleatoriamente os caracteres
        while (String(tempWord).equals(word)) { // enquanto a palavra embaralhada gerada for igual a original..
            tempWord.shuffle() //... continua embaralhando
        }
        return String(tempWord) // retorna a palavra embaralhada
    }

    fun resetGame() {// Usado para iniciar ou reiniciar o jogo.
        usedWords.clear() // Limpa o conjunto de palavras usadas
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle()) // _uiState.value é a forma de atribuir valor a esse state

    }

    init { // O bloco init é executado logo após o ViewModel ser criado. Isso garante que o jogo comece imediatamente com uma palavra embaralhada assim que o ViewModel estiver pronto.
        resetGame()
    }
}