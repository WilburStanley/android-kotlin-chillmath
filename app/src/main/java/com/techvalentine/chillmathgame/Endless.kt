package com.techvalentine.chillmathgame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.math.abs
import kotlin.math.pow
import kotlin.random.Random

class Endless : AppCompatActivity() {
    private lateinit var goBackToMainBtn: AppCompatImageButton
    private lateinit var inputEditText: EditText
    // Personalized Buttons
    private lateinit var negativeSignBtn: AppCompatButton
    private lateinit var decimalBtn: AppCompatButton
    private lateinit var backspaceBtn: AppCompatButton
    private lateinit var num1Btn: AppCompatButton
    private lateinit var num2Btn: AppCompatButton
    private lateinit var num3Btn: AppCompatButton
    private lateinit var num4Btn: AppCompatButton
    private lateinit var num5Btn: AppCompatButton
    private lateinit var num6Btn: AppCompatButton
    private lateinit var num7Btn: AppCompatButton
    private lateinit var num8Btn: AppCompatButton
    private lateinit var num9Btn: AppCompatButton
    private lateinit var num0Btn: AppCompatButton
    private lateinit var enterBtn: AppCompatButton
    // TextView
    private lateinit var displayedEquationTxt: TextView
    private lateinit var correctAnsTxt: TextView;
    // Custom Designed Bt
    private lateinit var additionBtn: RelativeLayout
    private lateinit var subtractionBtn: RelativeLayout
    private lateinit var multiplicationBtn: RelativeLayout
    private lateinit var divisionBtn: RelativeLayout
    private lateinit var allOfTheAboveBtn: RelativeLayout
    // Displays: LinearLayout, RelativeLayout
    private lateinit var gameSetupContainer: LinearLayout
    private lateinit var mainContainer: RelativeLayout
    private lateinit var correctDisplayLayout: RelativeLayout
    private lateinit var incorrectDisplayLayout: RelativeLayout
    private lateinit var navigationBarLayout: LinearLayout

    private var currentEquation: String = ""
    private var chosenOperator: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_endless)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.endless)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialization()
        setButtonListeners()

        additionBtn.setOnClickListener {
            chosenOperator = "addition"
            startGame()
        }
        subtractionBtn.setOnClickListener {
            chosenOperator = "subtraction"
            startGame()
        }
        multiplicationBtn.setOnClickListener {
            chosenOperator = "multiplication"
            startGame()
        }
        divisionBtn.setOnClickListener {
            chosenOperator = "division"
            startGame()
        }
        allOfTheAboveBtn.setOnClickListener {
            chosenOperator = "AllOfTheAbove"
            startGame()
        }

        enterBtn.setOnClickListener {
            val userAnswer = inputEditText.text.toString().trim()
            if (userAnswer.isEmpty()) {
                Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show()
            } else {
                checkAnswer(userAnswer)
                generateNewEquation(chosenOperator) // Generate new equation after checking
                inputEditText.text.clear() // Clear input field for next answer
            }
        }

        goBackToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            AppUtility.goToNewPageWithAnimation(this, intent)
        }
    }
    private fun startGame() {
        gameSetupContainer.visibility = View.GONE
        mainContainer.visibility = View.VISIBLE
        generateNewEquation(chosenOperator)
    }
    private fun displayCorrectLayout() {
        correctDisplayLayout.visibility = View.VISIBLE
        mainContainer.visibility = View.GONE
        navigationBarLayout.visibility = View.GONE

        val greenColor = ContextCompat.getColor(this, R.color.light_green)
        val whiteColor = ContextCompat.getColor(this, R.color.white)
        val endlessLayout: RelativeLayout = findViewById(R.id.endless)
        endlessLayout.setBackgroundColor(greenColor)

        Handler(Looper.getMainLooper()).postDelayed({
            correctDisplayLayout.visibility = View.GONE
            navigationBarLayout.visibility = View.VISIBLE
            mainContainer.visibility = View.VISIBLE
            endlessLayout.setBackgroundColor(whiteColor)
        }, 2000)
    }

    @SuppressLint("SetTextI18n")
    private fun displayIncorrectLayout(correctAnswer: Double) {
        incorrectDisplayLayout.visibility = View.VISIBLE
        mainContainer.visibility = View.GONE
        navigationBarLayout.visibility = View.GONE

        correctAnsTxt.text = "Correct answer: \n$correctAnswer"

        val redColor = ContextCompat.getColor(this, R.color.red)
        val whiteColor = ContextCompat.getColor(this, R.color.white)
        val endlessLayout: RelativeLayout = findViewById(R.id.endless)
        endlessLayout.setBackgroundColor(redColor)

        Handler(Looper.getMainLooper()).postDelayed({
            incorrectDisplayLayout.visibility = View.GONE
            navigationBarLayout.visibility = View.VISIBLE
            mainContainer.visibility = View.VISIBLE
            endlessLayout.setBackgroundColor(whiteColor)
        }, 2000)
    }
    private fun generateNewEquation(operator: String) {
        val num1 = Random.nextInt(1, 101)
        val num2 = Random.nextInt(1, 101)
        val operators = arrayOf('+', '-', '*', '/')

        val finalOperator: Char = when (operator) {
            "addition" -> operators[0]
            "subtraction" -> operators[1]
            "multiplication" -> operators[2]
            "division" -> operators[3]
            "AllOfTheAbove" -> operators.random()
            else -> operators.random()
        }

        val displayOperator = when (finalOperator) {
            '*' -> 'x'
            '/' -> '÷'
            else -> finalOperator
        }
        currentEquation = "$num1 $finalOperator $num2"

        val finalDisplay = "$num1 $displayOperator $num2"
        displayedEquationTxt.text = finalDisplay
    }

    private fun checkAnswer(userAnswer: String) {
        val answer = calculateResult(currentEquation)
        val tolerance = calculateTolerance(userAnswer)
        val userAnswerDouble = userAnswer.toDoubleOrNull()

        if (userAnswerDouble != null) {
            println("Calculated answer: $answer, User answer: $userAnswerDouble, Tolerance: $tolerance")
        }
        val isCorrect = userAnswerDouble != null && abs(answer - userAnswerDouble) <= tolerance

        if (isCorrect) displayCorrectLayout() else displayIncorrectLayout(answer)
    }

    private fun calculateResult(equation: String): Double {
        val parts = equation.split(" ")
        val num1 = parts[0].toDouble()
        val operator = parts[1]
        val num2 = parts[2].toDouble()

        return when (operator) {
            "*" -> num1 * num2
            "/" -> num1 / num2
            "-" -> num1 - num2
            "+" -> num1 + num2
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }
    private fun calculateTolerance(userAnswer: String): Double {
        val decimalPlaces = userAnswer.split(".").getOrNull(1)?.length ?: 0
        return if (currentEquation.contains("/")) 1 / (10.0.pow(decimalPlaces)) else 0.0001
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun setButtonListeners() {
        // Handler for backspace deletion speed
        val handler = Handler(Looper.getMainLooper())
        val deleteRunnable = object : Runnable {
            override fun run() {
                val currentText = inputEditText.text.toString()
                if (currentText.isNotEmpty()) {
                    inputEditText.setText(currentText.dropLast(1))
                    inputEditText.setSelection(inputEditText.length()) // Move cursor to end
                    handler.postDelayed(this, 50) // Repeat delete every 50ms
                }
            }
        }
        // Define a function to append text to the EditText
        val appendToInput: (String) -> Unit = { text ->
            inputEditText.append(text)
        }
        // Set up listeners for each button
        negativeSignBtn.setOnClickListener { appendToInput("-") }
        decimalBtn.setOnClickListener { appendToInput(".") }
        num1Btn.setOnClickListener { appendToInput("1") }
        num2Btn.setOnClickListener { appendToInput("2") }
        num3Btn.setOnClickListener { appendToInput("3") }
        num4Btn.setOnClickListener { appendToInput("4") }
        num5Btn.setOnClickListener { appendToInput("5") }
        num6Btn.setOnClickListener { appendToInput("6") }
        num7Btn.setOnClickListener { appendToInput("7") }
        num8Btn.setOnClickListener { appendToInput("8") }
        num9Btn.setOnClickListener { appendToInput("9") }
        num0Btn.setOnClickListener { appendToInput("0") }
        // Set up backspace button to remove the last character
        backspaceBtn.setOnClickListener {
            val currentText = inputEditText.text.toString()
            if (currentText.isNotEmpty()) {
                inputEditText.setText(currentText.dropLast(1))
                inputEditText.setSelection(inputEditText.length()) // Move cursor to end
            }
        }
        // Start deletion when backspace is long-pressed
        backspaceBtn.setOnLongClickListener {
            handler.post(deleteRunnable) // Start deletion on long press
            true // Return true to indicate the long press is handled
        }
        // Stop deletion when backspace is released
        backspaceBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                handler.removeCallbacks(deleteRunnable) // Stop deletion on release
            }
            false
        }
        touchEffectInitialization()
    }
    private fun initialization() {
        goBackToMainBtn = findViewById(R.id.goBackToMainBtn)
        // Edit Text
        inputEditText = findViewById(R.id.inputEditText)
        // TextView
        displayedEquationTxt = findViewById(R.id.displayedEquationTxt)
        correctAnsTxt = findViewById(R.id.correctAnsTxt)

        // Personalize Button
        negativeSignBtn = findViewById(R.id.negativeSignBtn)
        decimalBtn = findViewById(R.id.decimalPointBtn)
        backspaceBtn = findViewById(R.id.backspaceBtn)
        num1Btn = findViewById(R.id.num1Btn)
        num2Btn = findViewById(R.id.num2Btn)
        num3Btn = findViewById(R.id.num3Btn)
        num4Btn = findViewById(R.id.num4Btn)
        num5Btn = findViewById(R.id.num5Btn)
        num6Btn = findViewById(R.id.num6Btn)
        num7Btn = findViewById(R.id.num7Btn)
        num8Btn = findViewById(R.id.num8Btn)
        num9Btn = findViewById(R.id.num9Btn)
        num0Btn = findViewById(R.id.num0Btn)
        enterBtn = findViewById(R.id.enterBtn)
        // Custom Designed Btn
        additionBtn = findViewById(R.id.additionBtn)
        subtractionBtn = findViewById(R.id.subtractionBtn)
        multiplicationBtn = findViewById(R.id.multiplicationBtn)
        divisionBtn = findViewById(R.id.divisionBtn)
        allOfTheAboveBtn = findViewById(R.id.allOfTheAboveBtn)
        // Display
        gameSetupContainer = findViewById(R.id.gameSetupContainer)
        mainContainer = findViewById(R.id.mainContainer)
        correctDisplayLayout = findViewById(R.id.correctDisplayLayout)
        incorrectDisplayLayout = findViewById(R.id.incorrectDisplayLayout)
        navigationBarLayout = findViewById(R.id.navigationBar)

        AppUtility.setButtonTouchEffect(additionBtn)
        AppUtility.setButtonTouchEffect(subtractionBtn)
        AppUtility.setButtonTouchEffect(multiplicationBtn)
        AppUtility.setButtonTouchEffect(divisionBtn)
        AppUtility.setButtonTouchEffect(allOfTheAboveBtn)
    }
    private fun touchEffectInitialization() {
        AppUtility.setButtonTouchEffect(negativeSignBtn)
        AppUtility.setButtonTouchEffect(decimalBtn)
        AppUtility.setButtonTouchEffect(backspaceBtn)
        AppUtility.setButtonTouchEffect(num1Btn)
        AppUtility.setButtonTouchEffect(num2Btn)
        AppUtility.setButtonTouchEffect(num3Btn)
        AppUtility.setButtonTouchEffect(num4Btn)
        AppUtility.setButtonTouchEffect(num5Btn)
        AppUtility.setButtonTouchEffect(num6Btn)
        AppUtility.setButtonTouchEffect(num7Btn)
        AppUtility.setButtonTouchEffect(num8Btn)
        AppUtility.setButtonTouchEffect(num9Btn)
        AppUtility.setButtonTouchEffect(num0Btn)
        AppUtility.setButtonTouchEffect(enterBtn)
    }
}
