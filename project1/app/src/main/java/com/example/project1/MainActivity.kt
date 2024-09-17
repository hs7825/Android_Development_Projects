package com.example.project1

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility

class MainActivity : AppCompatActivity() {
    private var attemptCount = 0


    private fun addGuessToView(guess: String, result: String, attempt: Int) {

        if (attempt == 1) {
            val userFirstAttemptFeedback = findViewById<TextView>(R.id.userFirstAttemptFeedback)
            val userFirstAttempText = findViewById<TextView>(R.id.userFirstAttempText)
            val displayFirstAttemptNumber = findViewById<TextView>(R.id.displayFirstAttemptNumber)

            userFirstAttemptFeedback.text = "Guess 1 check: " + result
            userFirstAttempText.text = "Guess 1: " + guess

            userFirstAttemptFeedback.visibility = View.VISIBLE
            userFirstAttempText.visibility = View.VISIBLE
            displayFirstAttemptNumber.visibility = View.VISIBLE


        } else if (attempt == 2) {
            val userSecondAttemptFeedback = findViewById<TextView>(R.id.userSecondAttemptFeedback)
            val userSecondAttempText = findViewById<TextView>(R.id.userSecondAttempText)
            val displaySecondAttemptNumber = findViewById<TextView>(R.id.displaySecondAttemptNumber)

            userSecondAttemptFeedback.text = "Guess 2 check: " + result
            userSecondAttempText.text = "Guess 2: " + guess

            userSecondAttemptFeedback.visibility = View.VISIBLE
            userSecondAttempText.visibility = View.VISIBLE
            displaySecondAttemptNumber.visibility = View.VISIBLE
        } else if (attempt == 3) {
            val userThirdAttemptFeedback = findViewById<TextView>(R.id.userThirdAttemptFeedback)
            val userThirdAttempText = findViewById<TextView>(R.id.userThirdAttempText)
            val displayThirdAttemptNumber = findViewById<TextView>(R.id.displayThirdAttemptNumber)

            userThirdAttemptFeedback.text = "Guess 3 check: " + result
            userThirdAttempText.text = "Guess 3: " + guess

            userThirdAttemptFeedback.visibility = View.VISIBLE
            userThirdAttempText.visibility = View.VISIBLE
            displayThirdAttemptNumber.visibility = View.VISIBLE
        }

    }


    private fun checkGuess(guess: String, wordToGuess: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val submitGuessButton = findViewById<Button>(R.id.submitGuessButton)
        val userText = findViewById<EditText>(R.id.userText)
        var randomWord = FourLetterWordList.getRandomFourLetterWord()
        val displayCorrectWord = findViewById<TextView>(R.id.displayCorrectWord)

        // Initialize the game UI
        resetGameUI()

        submitGuessButton.setOnClickListener {
            if (submitGuessButton.text == "Restart Game") {
                // Reset the game if the button text indicates a restart
                resetGameUI()
                randomWord = FourLetterWordList.getRandomFourLetterWord()
                return@setOnClickListener
            }

            val userGuess = userText.text.toString().uppercase()

            // Validate guess length
            if (userGuess.length != 4) {
                Toast.makeText(
                    this,
                    "Invalid guess! Please enter a 4-letter word.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Handle attempts and guesses
            if (attemptCount < 3) {
                attemptCount++
                val result = checkGuess(userGuess, randomWord)

                addGuessToView(userGuess, result, attemptCount)
                if(randomWord == userGuess){
                    var blackCheckImage = findViewById<ImageView>(R.id.guessedCorrectlyImage)
                    blackCheckImage.visibility = View.VISIBLE
                    displayCorrectWord.text = randomWord
                    displayCorrectWord.visibility = View.VISIBLE
                    submitGuessButton.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.white
                        )
                    )
                    submitGuessButton.setTextColor(Color.parseColor("#FF0000"))
                    submitGuessButton.text = "Restart Game"
                    return@setOnClickListener


                }

                // Max attempts reached
                if (attemptCount == 3) {
                    Toast.makeText(this, "Max attempts reached!", Toast.LENGTH_SHORT).show()
                    displayCorrectWord.text = randomWord
                    displayCorrectWord.visibility = View.VISIBLE
                    submitGuessButton.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.white
                        )
                    )
                    submitGuessButton.setTextColor(Color.parseColor("#FF0000"))
                    submitGuessButton.text = "Restart Game"
                }
            }
        }
    }

    private fun resetGameUI() {
        attemptCount = 0
        val submitGuessButton = findViewById<Button>(R.id.submitGuessButton)
        val displayCorrectWord = findViewById<TextView>(R.id.displayCorrectWord)
        val userText = findViewById<EditText>(R.id.userText)

        // Reset UI elements
        userText.text.clear()
        displayCorrectWord.visibility = View.INVISIBLE
        submitGuessButton.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.holo_blue_light
            )
        )
        submitGuessButton.setTextColor(Color.BLACK)
        submitGuessButton.text = "Submit Guess"

        // Hide attempt feedbacks
        findViewById<TextView>(R.id.userFirstAttemptFeedback).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.userFirstAttempText).visibility = View.INVISIBLE
        findViewById<ImageView>(R.id.guessedCorrectlyImage).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.displayFirstAttemptNumber).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.userSecondAttemptFeedback).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.userSecondAttempText).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.displaySecondAttemptNumber).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.userThirdAttemptFeedback).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.userThirdAttempText).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.displayThirdAttemptNumber).visibility = View.INVISIBLE
    }
}



