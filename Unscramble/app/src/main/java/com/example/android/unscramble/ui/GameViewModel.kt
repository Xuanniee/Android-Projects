package com.example.android.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.MAX_NO_OF_WORDS
import com.example.android.unscramble.data.SCORE_INCREASE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.update

// Using a ViewModel allows us to preserve data even after a Configuration Change like Rotation
class GameViewModel : ViewModel() {
    // User's Guess Variable Property
    var userGuess by mutableStateOf("")
        private set

    // Game UI State
    private val _uiState = MutableStateFlow(GameUiState())

    // Backing Property for _uiState by allowing external controllers to easily access the values
    // Makes _uiState a readable only stateflow
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Picking and Scrambling a random word
    private lateinit var currentWord: String
    // New Property to act as a mutable set to store used words in the game
    private var usedWords: MutableSet<String> = mutableSetOf()

    // Init Block;
    // For some reason, this needs to be after all of the Variables to instantiate a ViewModel instance
    init {
        resetGame()
    }

    // Helper Function to shuffle; Returns a String as well
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            // Constantly shuffle so long as the word is not properly scrambled
            tempWord.shuffle()
        }
        // Return the Scrambled Word
        return String(tempWord)     // Convert to String since it was a Char Array
    }

    // Helper Function to Pick a Random Word + Shuffle
    private fun pickRandomWordAndShuffle(): String {
        // Constantly pick a new random word until it is an unused one
        currentWord = allWords.random()

        if (usedWords.contains(currentWord)) {
            // Select another random word
            return pickRandomWordAndShuffle()
        }
        else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    // Helper Function to initialise the game
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    // Function that updates the User's Guess by adding what they are typing
    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    // Helper Function to determine if User is Correct
    fun checkUserGuess() {
        // Checking if the Guess equals the Goal Word
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            // Correct Guess, Increase Score
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            // Update the Game State for the Next Round
            updateGameState(updatedScore)
        }
        else {
            // Wrong Guess
            _uiState.update { currentState ->
                // copy() function allows us to copy an object, while altering certain properties only
                currentState.copy(isGuessedWordWrong = true)
            }

        }

        // Reset User Guess
        updateUserGuess("")
    }

    // Helper Function to update the Game State, i.e. the Score mainly
    private fun updateGameState(updatedScore: Int) {
        // If Loop to check if Game Ended; In Data File
        if (usedWords.size == MAX_NO_OF_WORDS) {
            // Last Round of Game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc(),
                    isGameOver = true
                )
            }
        }
        else {
            // Normal Round in Game
            _uiState.update { currentState ->
                // Changing select properties of the object
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentWordCount = currentState.currentWordCount.inc(),
                    // Changing the Current Scrambled Word
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    score = updatedScore
                )

            }
        }
    }

    // Helper Function to Skip Word
    fun skipWord() {
        // Score Remains the Same
        updateGameState(updatedScore = _uiState.value.score)
        // Reset User Guess
        updateUserGuess("")
    }
}






