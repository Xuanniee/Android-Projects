package com.example.android.unscramble.ui.test

import com.example.android.unscramble.data.SCORE_INCREASE
import com.example.android.unscramble.data.getUnscrambledWord
import com.example.android.unscramble.ui.GameViewModel
import junit.framework.Assert.*
import org.junit.Assert.assertNotEquals
import org.junit.Test


// Always Test with Coverage to ensure every method and line of code has been tested
class GameViewModelTest {
    // Declare a ViewModel Property and Assign an instance of GameViewModel Class to it
    private val viewModel = GameViewModel()

    // Naming of Tests
    // thingUnderTest_TriggerOfTest_ResultOfTest format to name the test function name

    // Defining a Companion Object for Reusability
    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANS = SCORE_INCREASE
    }

    // Success Path Unit Tests
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        // Get the Correct Word
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        // Verify if the Guess is Correct by Simulating the Player providing the Correct Word
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        // Updating the GameUiState
        currentGameUiState = viewModel.uiState.value

        // Assert that the Guessed Word is Correct and the Score is updated
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANS, currentGameUiState.score) // Each Correct Ans gives 20 points
    }

    // Improve Coverage by adding another Test for when User skips
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
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANS, currentGameUiState.score)
        // Assert that word count is increased by 1 after word is skipped.
        assertEquals(lastWordCount + 1, currentGameUiState.currentWordCount)
    }

    // Error Path Tests

    // Test for Incorrect Guess; Score Unchanged
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // Definite Incorrect Word; i.e. A word not in the List
        val incorrectPlayerWord = "and"

        // Simulate User Giving Input and Checking
        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        var currentUiState = viewModel.uiState.value

        // Assert Score is 0
        assertEquals(0, currentUiState.score)
        // Assert Answer is Wrong
        assertTrue(currentUiState.isGuessedWordWrong)
    }

    // Boundary Case Tests

    // Assert that GameViewModel is initialised Properly
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unscrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        // Assertions for when Game is initially initialised
        // Word is Scrambled
        assertNotEquals(unscrambledWord, gameUiState.currentScrambledWord)
        // Word Count is 1, the first
        assertTrue(gameUiState.currentWordCount == 1)
        // Score is 0
        assertTrue(gameUiState.score == 0)
        // Wrong Guess is Currently False
        assertFalse(gameUiState.isGuessedWordWrong)
        // Game is not Over
        assertFalse(gameUiState.isGameOver)
    }
}