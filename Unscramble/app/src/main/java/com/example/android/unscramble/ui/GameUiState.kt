package com.example.android.unscramble.ui

// Model Class for State UI
data class GameUiState(
    // Properties that the Ui State must have like the Score
    val currentScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,
    val score: Int = 0,
    // Number of Words played
    val currentWordCount: Int = 1,
    // Boolean for Game over
    val isGameOver: Boolean = false
)
