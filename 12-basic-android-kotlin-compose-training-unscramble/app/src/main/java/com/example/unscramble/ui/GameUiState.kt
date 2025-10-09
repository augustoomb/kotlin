package com.example.unscramble.ui

data class GameUiState(
    val currentScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false, //* se o usuário errou o palpite, vira true
    val score: Int = 0, // ** pontos
    val currentWordCount: Int = 1, // ** contador de palavras
    val isGameOver: Boolean = false // ***
)